# Ders 11 — Repository ve Spring Data JPA

## Nasıl çalışır
`interface CustomerRepository extends JpaRepository<Customer, Long>` →
Spring açılışta proxy üretir; CRUD `SimpleJpaRepository`'den gelir. Sınıf yazılmaz.

## Derived query (metot isminden sorgu)
```java
findByCustomerNumber(String)           → WHERE customer_number = ?
findByCustomerTypeAndStatus(...)       → WHERE type = ? AND status = ?
findByTitleContainingIgnoreCase(...)   → WHERE UPPER(title) LIKE UPPER('%?%')
existsByIdentifier(String)             → SELECT 1 ... LIMIT 1 (nesne yüklemez)
```
Alan adı yanlışsa uygulama **açılışta** patlar (erken hata = iyi).

## Optional
`findById → Optional<T>`: bulunamama ihtimali tipe gömülü.
```java
repo.findByX(n).map(Dto::from).orElseThrow(() -> new ResourceNotFoundException(...))
```
⚠️ `orElse(f())` → f() Optional dolu olsa bile ÇALIŞIR; `orElseGet(() -> f())` → sadece boşsa.

## Page + Pageable
`Page<T>` içerik + toplam sayı döner (`LIMIT/OFFSET` + `COUNT`). Milyonluk tabloda `List` dönme.
`page.map(Dto::from)` sayfa yapısını koruyarak dönüştürür.

## @Query
Sıra: önce derived → JPQL (`@Query("select c from Customer c ...")` — entity adları, DB bağımsız)
→ mecbursan native (`nativeQuery=true`, ör. `nextval('seq')`).
`join fetch` → ilişkiyi TEK sorguda yükler (N+1 çözümü #1).

## 🎯 Mülakat
**S: Repository interface'inin implementasyonu nerede?**
C: Spring açılışta proxy üretir; CRUD SimpleJpaRepository'den, derived query'ler metot adının
parse edilmesiyle gelir. Karmaşık sorgularda @Query ile JPQL, gerekirse native SQL yazarım.
