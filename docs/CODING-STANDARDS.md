# Kod Standartları

Bu doküman projede uygulanan kodlama ve mimari kurallarını tanımlar.

---

## 1. Dil Kuralları

| Ne | Dil |
|---|---|
| Sınıf, alan, metot adları | İngilizce |
| Yorumlar ve Javadoc | İngilizce |
| Log mesajları | İngilizce |
| Test metot adları | İngilizce (`shouldRejectWhenLimitInsufficient`) |
| Commit mesajları | İngilizce (Conventional Commits) |
| Teknik exception mesajları | İngilizce |
| Kullanıcıya görünen arayüz metinleri | Türkçe |
| Kullanıcıya görünen doğrulama mesajları | Türkçe |
| `docs/` ve `README.md` | Türkçe |

Bankacılık terimlerinin karşılıkları için: [`BANKACILIK-SOZLUGU.md`](BANKACILIK-SOZLUGU.md)

---

## 2. Yorum Politikası

Kod, yorum olmadan okunabilir olmalıdır. Yorum bir açıklama aracı değil, **istisna** aracıdır.

**Yorum yazılmaz:**
- Kodun ne yaptığını tekrar eden yorumlar (`// save the customer`)
- Sınıf veya katman tanıtan başlık yorumları
- Bariz olanı söyleyen yorumlar (`// constructor`, `// getter`)

**Yorum yazılır:**
- Bir bankacılık kuralının kaynağı veya yasal dayanağı
  ```java
  // BSMV applies at 5% on interest for commercial loans; KKDF does not apply.
  ```
- Sezgiye aykırı teknik karar
  ```java
  // Scale 6 is required here; rounding earlier drifts the final installment.
  ```
- Bilinen kısıt veya geçici çözüm
- Javadoc: yalnızca public API ve karmaşık hesaplama metotlarında

---

## 3. Katmanlı Mimari

```
Controller  →  Service  →  Repository  →  Database
   HTTP        iş kuralı     veri erişimi
```

| Katman | Sorumluluk | Burada bulunmaz |
|---|---|---|
| Controller | HTTP isteğini karşılama, doğrulama, yanıt üretme | İş kuralı, SQL |
| Service | İş kuralları, transaction sınırı | HTTP nesneleri, SQL |
| Repository | Veritabanı erişimi | İş kuralı |
| Entity | Veritabanı tablosunun karşılığı | HTTP/JSON mantığı |

**Kurallar**
- Controller katmanında iş kuralı yazılmaz.
- Repository katmanında iş kuralı yazılmaz.
- Dışarıya Entity dönülmez; DTO dönülür.
- `@Transactional` Service katmanında tanımlanır.

---

## 4. Paketleme

Paketler teknik katmana göre değil, **iş alanına göre** ayrılır:

```
com.kredilendirme.loan
├── customer/      → controller, service, repository, entity, dto
├── product/
├── application/
├── limit/
└── repayment/
```

Gerekçe: Teknik katmana göre paketleme, sistem büyüdükçe tek bir `service` paketinde
onlarca ilgisiz sınıf toplanmasına yol açar. İş alanına göre paketleme hem bakımı
kolaylaştırır hem de paket sınırlarını ileride servis sınırı olarak kullanmayı mümkün kılar.

---

## 5. Veri ve Tip Kuralları

- Parasal tutarlar için **her zaman** `BigDecimal` kullanılır. `double` ve `float` kullanılmaz.
- Veritabanında parasal alanlar `NUMERIC(19,4)` tipindedir.
- Enum alanları `@Enumerated(EnumType.STRING)` ile saklanır. `ORDINAL` kullanılmaz.
- Tarih alanları için `LocalDate` / `LocalDateTime` kullanılır.
- Faiz ve vergi oranları koda gömülmez; geçerlilik tarihiyle birlikte veritabanında tutulur.

---

## 6. Bağımlılık Yönetimi

- **Constructor injection** kullanılır.
- Alan üzerinde `@Autowired` (field injection) kullanılmaz.

Gerekçe: Constructor injection zorunlu bağımlılıkları derleme zamanında görünür kılar,
alanların `final` olmasını sağlar ve sınıfın Spring bağlamı olmadan test edilmesine izin verir.

---

## 7. Test

- Servis katmanı testleri Mockito ile izole yazılır.
- Repository testleri `@DataJpaTest` ile yazılır.
- Controller testleri `@WebMvcTest` + `MockMvc` ile yazılır.
- Tüm uygulamayı ayağa kaldıran `@SpringBootTest` yalnızca uçtan uca senaryolarda kullanılır.
- Hesaplama sınıfları `@ParameterizedTest` ile, elle doğrulanmış bankacılık örnekleri üzerinden test edilir.

---

## 8. Sürüm Kontrolü

- Dal yapısı: `main` ← `develop` ← `feature/*`
- `main` dalına doğrudan commit yapılmaz.
- Commit mesajları [Conventional Commits](https://www.conventionalcommits.org) biçimindedir:

```
feat(limit): add optimistic locking to credit limit
fix(repayment): write rounding difference to the final installment
test(calculation): cover annuity schedule with real bank examples
docs(adr): record the decision to use orchestration-based saga
```
