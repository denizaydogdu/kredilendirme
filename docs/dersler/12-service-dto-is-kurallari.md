# Ders 12 — Service Katmanı, DTO ve İş Kuralları

## DTO neden zorunlu (4 sebep — ezberle)
1. **Lazy patlaması**: Jackson serialize ederken lazy alana dokunur → exception/gizli sorgu.
2. **Hassas alan sızıntısı**: entity'ye eklenen alan otomatik API'ye düşer.
3. **API↔şema kilitlenmesi**: kolon adı değişince dış sözleşme kırılır; DTO tampondur.
4. **Sonsuz döngü**: çift yönlü ilişkide StackOverflow.

Request DTO: Bean Validation anotasyonları (`@NotBlank` — null+boş+boşluk reddeder, `@Pattern`).
Response DTO: final alanlar + private constructor + `static from(entity)` (static factory).

## Doğrulama iki seviye
- **Biçimsel** → DTO anotasyonları, Controller sınırında (`@Valid`).
- **İş kuralı** → Service'te (TCKN checksum, mükerrer kayıt — DB/domain bilgisi ister).

## Exception hiyerarşisi
`BusinessException extends RuntimeException` (unchecked) ← `ResourceNotFoundException`.
Unchecked seçilir çünkü: imza kirliliği olmaz VE **@Transactional varsayılan olarak sadece
unchecked'te rollback yapar** (checked'te COMMIT eder — tuzak soru!).

## Constructor injection (4 gerekçe)
final alanlar → değişmezlik · Spring'siz test (`new Service(mock)`) · bağımlılık imzada görünür ·
circular dependency açılışta patlar. Tek constructor'da `@Autowired` gerekmez.

## Transaction deseni
```java
@Service @Transactional(readOnly = true)   // sınıf varsayılanı: okuma
class CustomerService {
    @Transactional                          // yazan metot ezer
    public ... create(...) { ... }
```
`readOnly=true`: dirty-check snapshot alınmaz, niyet belgelenir.

## Dirty checking pratiği
`update()` metodunda `save()` YOK: findById ile gelen persistent nesnenin setter'ı çağrılır,
commit'te Hibernate UPDATE atar.

## Sequence ile numara üretimi
`count()+1` → iki eşzamanlı istek aynı numarayı alır. Doğrusu DB sequence:
`@Query(value="select nextval('customer_number_seq')", nativeQuery=true)`.

## TCKN/VKN (Stream ile)
```java
int[] d = value.chars().map(Character::getNumericValue).toArray();
int odd = IntStream.of(0,2,4,6,8).map(i -> d[i]).sum();
// d10 = ((odd*7 - even) % 10  — Java % negatif dönebilir! d10 >= 0 kontrolü şart
```
TCKN: d10=((tek7×)−çift)%10, d11=(ilk10)%10. Test değerleri: geçerli 12345678950 / 1234567890.
