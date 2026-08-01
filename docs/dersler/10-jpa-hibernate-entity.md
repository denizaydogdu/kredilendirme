# Ders 10 — JPA, Hibernate, Entity ve 1:1 İlişki

## Üçlü ayrım
```
Spring Data JPA  → kolaylık katmanı (findByX'ten sorgu türetir)
  JPA            → STANDART (@Entity, @Id — arayüz, çalışmaz)
    Hibernate    → UYGULAMA (SQL'i üreten motor)
```

## Entity yaşam döngüsü
```
TRANSIENT → save() → PERSISTENT (izleniyor!) → tx biter → DETACHED     (+ REMOVED)
```
**Dirty checking:** Persistent nesnenin alanını değiştir, `save()` çağırma —
transaction commit'inde Hibernate farkı görüp UPDATE atar.

## BaseEntity içindekiler
- `@MappedSuperclass`: kendisi tablo değil; alanları çocuk tablolara kolon olur.
- `@GeneratedValue(IDENTITY)`: DB auto-increment. (SEQUENCE batch insert yapabilir, IDENTITY yapamaz.)
- `@CreatedDate/@LastModifiedDate` + `AuditingEntityListener`: otomatik denetim izi
  (`@EnableJpaAuditing` config gerekir).
- `@Version`: optimistic locking — UPDATE'e `WHERE version=?` ekler; tutmayan taraf
  `OptimisticLockException` alır. Kilit yok, çakışma yazma anında yakalanır.

## equals/hashCode kuralı
`hashCode` id'ye DAYANMAZ (id save'de null→42 değişir, nesne HashSet'te kaybolur) → sabit değer.
`equals` id ile ama `id != null` şartıyla. Lombok `@Data` entity'de KULLANILMAZ (tüm alanlar +
lazy ilişkiler dahil olur).

## @Enumerated: HER ZAMAN STRING
`ORDINAL` sıra saklar; enum'a eleman eklenince/sıralanınca DB'deki tüm değerler sessizce kayar.

## 1:1 — mappedBy kimde?
FK hangi tablodaysa **owning side orası** → `@JoinColumn` orada.
Karşı taraf `mappedBy="..."` yazar. Unutulursa Hibernate fazladan ilişki/kolon üretir.
`cascade=ALL` sahiple birlikte kaydet/sil; `orphanRemoval=true` sahipsiz kalan kayıt silinir.

**Senior:** `@OneToOne`'un mappedBy tarafında LAZY çoğu zaman çalışmaz — Hibernate null mu proxy
mi bilmek için karşı tabloyu sorgulamak zorundadır. Çözüm: tek yönlü ilişki veya `@MapsId`.

## @Embeddable
Kimliksiz value object (Address, Money); kendi tablosu yok, sahibinin tablosuna gömülür.
Protected no-arg constructor Hibernate içindir; setter'sız = immutable.
