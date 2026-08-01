# Ders 14 — Unit Test: JUnit 5 + Mockito

## Test piramidi (kendi sayılarımızla!)
| Seviye | Sınıf | Test | Süre |
|---|---|---|---|
| Unit (saf) | TaxNumberValidatorTest | 15 | **0.075 sn** |
| Unit (Mockito) | CustomerServiceTest | 5 | 0.58 sn |
| Slice (H2) | CustomerRepositoryTest | 6 | **12.65 sn** |

168 kat hız farkı → tabanda çok unit test olmasının sebebi.

## JUnit 5
- `@ParameterizedTest` + `@ValueSource/@CsvSource/@NullAndEmptySource`: aynı test, n veri.
- `@DisplayName`: raporda okunur isim. Metot adı davranışı söyler: `shouldRejectInvalidTckn`.
- AssertJ: `assertThat(x).isTrue()`, `assertThatThrownBy(...).isInstanceOf(...)` — akıcı + iyi mesaj.
- AAA düzeni: Arrange → Act → Assert.

## Mockito sözlüğü
```java
@ExtendWith(MockitoExtension.class)
@Mock CustomerRepository repo;          // dublör
@InjectMocks CustomerService service;   // GERÇEK sınıf + mock'lar constructor'a
@Captor ArgumentCaptor<Customer> captor;

when(repo.existsByIdentifier("x")).thenReturn(false);          // senaryo
when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));  // dinamik cevap
verify(repo, never()).save(any());                             // OLMAMASI gerekeni kanıtla
verify(repo).save(captor.capture());                           // giden argümanı yakala
```
- `@Spy`: gerçek nesne, sadece seçili metotlar ezilir.
- ⚠️ Matcher tuzağı: bir argümanda `any()` varsa hepsi matcher olmalı → `eq("sabit")`.
- Strict stubbing: kullanılmayan `when` → test fail (ölü senaryo birikmez).

## @DataJpaTest
Sadece JPA dilimi + otomatik H2; her test **rollback** → testler birbirini kirletmez.
- Flyway kapalı (migration'lar PG'ye özgü) → şema entity'lerden. Bedel: dialect test edilmez,
  sequence yok. Gerçek çözüm: **Testcontainers** (Docker'da gerçek PG).
- `@Import(JpaAuditingConfig.class)`: slice, sıradan @Configuration'ları YÜKLEMEZ.

## 🎯 Mülakat
**S: Neden her testi @SpringBootTest yapmıyorsunuz?**
C: Tüm context'i kaldırır, yavaştır. Slice testler ilgili dilimi yükler; projemde saf unit 75ms,
JPA slice 12sn — piramidin tabanı bu maliyet farkı yüzünden unit testtir.
