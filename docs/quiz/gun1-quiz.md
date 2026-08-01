# Gün 1 Quiz — 50 Soru (basitten zora)

Önce cevapları kendin ver, sonra en alttaki cevap anahtarıyla karşılaştır. Hedef: 40+/50.

---

## Seviye 1 — Isınma (1-12)

1. JDK, JRE ve JVM arasındaki fark nedir?
2. Kredinin üç temel bileşeni nedir? (Türkçe + kodda kullandığımız İngilizce adlarıyla)
3. Nakit kredi ile gayrinakdi kredi arasındaki temel fark nedir?
4. `git branch` ne işe yarar? Bizim projede hangi üç seviye dal var?
5. Maven'da GAV neyi ifade eder?
6. `List`, `Set` ve `Map` arasındaki fark nedir?
7. Bir Spring bean'i nedir?
8. `@RestController` ile `@Controller` farkı nedir?
9. HTTP'de GET, POST, PUT, DELETE ne için kullanılır?
10. Entity nedir? `@Entity` anotasyonu ne yapar?
11. Lambda ifadesi nedir? Bir örnek yaz.
12. Unit test nedir, ne işe yarar?

## Seviye 2 — Temel Uygulama (13-25)

13. Limit tahsis ile kullandırım arasındaki farkı kredi kartı analojisiyle açıkla.
14. Spot kredide müşteri ara ödeme yapar mı? Faiz ne zaman belirlenir?
15. `.gitignore` dosyasına neden `target/` ve `.env` koyduk?
16. Maven dependency scope'ları nelerdir? PostgreSQL driver'ı neden `runtime`?
17. `@NotNull` ile `@NotBlank` farkı nedir?
18. IoC (Inversion of Control) ne demektir?
19. `@SpringBootApplication` hangi üç anotasyonu birleştirir?
20. `Optional` ne işe yarar? `findById` neden `Optional` döner?
21. Stream'de intermediate ve terminal operation farkı nedir? İkişer örnek ver.
22. `@PathVariable`, `@RequestParam`, `@RequestBody` hangi veriyi nereden okur?
23. DTO nedir? En az 3 sebeple entity'yi neden direkt dönmeyiz?
24. `@Mock` ile `@InjectMocks` farkı nedir?
25. Flyway migration dosya adı `V1__create_customer.sql` — buradaki kurallar nelerdir?

## Seviye 3 — Mekanizma Bilgisi (26-38)

26. Rotatif kredide faiz neyin üzerinden hesaplanır? Hiç çekmeyen müşteri faiz öder mi?
27. Eşit taksitli kredide ilk taksitin faiz payı neden yüksektir?
28. `mvn clean install` hangi fazları sırayla çalıştırır?
29. `dependencyManagement` ile `dependencies` farkı nedir?
30. Constructor injection'ın field injection'a 4 üstünlüğü nedir?
31. Bean scope'ları nelerdir? Singleton bean'de neden mutable state tutulmaz?
32. Entity lifecycle'ın 4 durumu nedir?
33. Dirty checking nedir? Hangi şartlarda çalışır?
34. `@Enumerated(EnumType.ORDINAL)` neden tehlikelidir? Felaket senaryosu anlat.
35. 1:1 ilişkide `mappedBy` hangi tarafa yazılır? Kural nedir?
36. `orElse` ile `orElseGet` farkı nedir? Hangisi ne zaman tehlikeli?
37. 400, 401, 403, 404, 422, 500 kodlarını birer cümleyle eşleştir.
38. `@DataJpaTest` neden her testi rollback yapar? `@SpringBootTest`'ten farkı ne?

## Seviye 4 — Derinlik (39-50)

39. `open-in-view: false` neden yaptık? `true` olsaydı hangi gizli problem oluşurdu?
40. `ddl-auto` değerlerini say; canlıda `update` neden felakettir, biz neden `validate` kullanıyoruz?
41. `HashMap` içeride nasıl çalışır? Java 8'de bucket'ta 8 eleman aşılınca ne olur?
42. Entity'de `hashCode()` neden `id` üzerinden hesaplanmaz? Senaryosuyla anlat.
43. `@Version` alanı SQL seviyesinde nasıl çalışır? İki şube senaryosunu adım adım anlat.
44. `map` ile `flatMap` farkını "müşteriler → tüm kredileri" örneğiyle kodla göster.
45. `Collectors.toMap`'in üçüncü parametresi nedir, yazılmazsa ne olur?
46. PECS kuralı nedir? `List<? extends Loan>`'a eleman eklenebilir mi, neden?
47. `BusinessException` neden `RuntimeException`'dan türedi? `@Transactional` rollback'iyle bağlantısı ne?
48. `@OneToOne`'un `mappedBy` tarafında LAZY neden çoğu zaman çalışmaz? Çözümleri neler?
49. Müşteri numarasını `count()+1` ile üretmek neden yanlış? Sequence bunu nasıl çözer, biz nasıl çağırdık?
50. Test piramidini bugünkü gerçek sayılarımızla savun (0.075 sn / 0.58 sn / 12.65 sn) ve H2 ile test etmenin bedelini + çözümünü söyle.

---
---

# CEVAP ANAHTARI

**1.** JVM bytecode'u çalıştıran sanal makine; JRE = JVM + standart kütüphaneler (çalıştırmaya yeter); JDK = JRE + javac/jar gibi geliştirme araçları. Kod yazan JDK kurar.

**2.** Anapara (`principal`), faiz (`interest`), vade (`maturity`/`term`).

**3.** Nakitte para çıkar, banka faiz kazanır. Gayrinakdide banka garanti verir (para çıkmaz), komisyon kazanır; risk şarta bağlıdır.

**4.** Bağımsız geliştirme hattı açar. Bizde: `main` (canlı, doğrudan commit yasak) ← `develop` ← `feature/gunX-konu`.

**5.** GroupId (üretici, ters domain) + ArtifactId (proje adı) + Version — kütüphanenin benzersiz adresi.

**6.** List sıralı ve tekrarlı; Set tekrarsız; Map anahtar-değer tutar, anahtar tekil.

**7.** Spring container'ının oluşturup yaşam döngüsünü yönettiği nesne.

**8.** `@RestController` = `@Controller` + `@ResponseBody`; dönen nesne JSON olur. `@Controller` view adı döner (Thymeleaf).

**9.** GET okur, POST oluşturur, PUT tam günceller, DELETE siler. GET/PUT/DELETE idempotent, POST değil.

**10.** Veritabanı tablosunun Java sınıfı karşılığı; `@Entity` sınıfı JPA'ya tanıtır, satır↔nesne eşlemesi yapılır.

**11.** Tek soyut metotlu arayüzün kısa implementasyonu: `loan -> loan.getAmount()`.

**12.** Tek bir sınıfı bağımlılıklarından izole, hızlı ve otomatik doğrulayan test; hatayı satır seviyesinde gösterir.

**13.** Tahsis = kart limiti (izin, para çıkmadı); kullandırım = kartla harcama (para fiilen çıktı). 1M tahsis − 400K kullanım = 600K kullanılabilir.

**14.** Hayır, ara ödeme yok; anapara+faiz vade sonunda tek seferde. Faiz kullandırım anında belirlenir ve sabitlenir.

**15.** `target/` üretilebilir derleme çıktısıdır, repoyu şişirir. `.env` şifre içerir; bir kez commit'lenen şey tarihçede kalır — şifre yanar.

**16.** compile / provided / runtime / test. Driver'ı kodda import etmeyiz; sadece çalışma anında JDBC yükler → runtime.

**17.** `@NotNull` sadece null'u reddeder ("" geçer); `@NotBlank` null + boş + sadece boşluğu reddeder — String için doğrusu.

**18.** Nesne oluşturma/bağlama kontrolünün uygulamadan container'a geçmesi; sınıf `new` yapmaz, bağımlılığı dışarıdan alır.

**19.** `@Configuration` + `@ComponentScan` (bulunduğu paketten aşağı tarar — ana sınıf kök pakette olmalı) + `@EnableAutoConfiguration`.

**20.** "Değer olmayabilir" bilgisini tipe taşır; çağıranı boş durumu ele almaya zorlar, NPE kaynağında yakalanır. Kayıt bulunamayabilir → Optional.

**21.** Intermediate lazy'dir, yeni stream döner: `filter`, `map`, `sorted`. Terminal akışı tüketir: `collect`, `sum`, `findFirst`, `forEach`.

**22.** PathVariable URL parçası (`/customers/{id}`); RequestParam query string (`?title=x`); RequestBody JSON gövde → DTO.

**23.** Lazy loading patlaması; hassas alan sızıntısı; API-şema kilitlenmesi; çift yönlü ilişkide sonsuz döngü.

**24.** `@Mock` sahte bağımlılık üretir; `@InjectMocks` test edilen GERÇEK sınıfı kurup mock'ları constructor'ına verir.

**25.** V + sürüm no + ÇİFT alt çizgi + açıklama. Uygulanmış migration asla değiştirilmez (checksum); değişiklik yeni V dosyasıyla yapılır.

**26.** Günlük kullanılan bakiye üzerinden; limitin kendisine faiz işlemez. Hiç çekmeyen faiz ödemez.

**27.** Faiz her ay kalan borçtan hesaplanır; başta borç en yüksek olduğundan taksitin büyük kısmı faizdir (amortisman).

**28.** validate → compile → test → package → verify → install (jar ~/.m2'ye kurulur). Öncesindeki tüm fazlar çalışır, test kırıksa durur.

**29.** `dependencies` fiilen ekler ve alt modüllere miras kalır; `dependencyManagement` sadece sürümü merkezî sabitler.

**30.** `final` alanlar; Spring'siz test (`new Service(mock)`); bağımlılıklar imzada görünür; circular dependency açılışta patlar.

**31.** singleton (varsayılan), prototype, request, session. Singleton'ı tüm istekler paylaşır → mutable state thread-safety bozar.

**32.** Transient (yeni) → Persistent (context izliyor) → Detached (context kapandı); ayrıca Removed.

**33.** Persistent nesnenin alanları değişirse commit'te Hibernate farkı görüp UPDATE atar. Şart: aktif transaction + managed entity.

**34.** Sırayı (0,1,2) saklar. Enum yeniden sıralanırsa/araya eleman girerse DB'deki tüm eski değerler sessizce başka sabite işaret eder. Bu yüzden her zaman STRING.

**35.** FK hangi tablodaysa owning side orasıdır (`@JoinColumn` orada); `mappedBy` FK'sız tarafa yazılır. Unutulursa Hibernate fazladan ilişki üretir.

**36.** `orElse(f())` → f() Optional dolu olsa bile çalışır; `orElseGet(() -> f())` → sadece boşsa. Pahalı üretimlerde orElse tehlikelidir.

**37.** 400 biçim bozuk; 401 kimlik doğrulanmadı; 403 kimliği biliyorum ama yetki yok; 404 kaynak yok; 422 biçim doğru ama iş kuralı reddetti; 500 sunucu hatası (bizim bug).

**38.** Testler birbirinin verisini görmesin diye her test transaction'ı geri alınır. `@SpringBootTest` tüm context'i kaldırır (yavaş); `@DataJpaTest` sadece JPA dilimi + H2.

**39.** `true` iken EntityManager isteğin sonuna dek açık kalır; view'daki lazy dokunuş sessizce sorgu atar → gizli N+1. `false` ile veri Service'te bilinçli yüklenir, dışarıda dokunuş gürültülü `LazyInitializationException` verir.

**40.** create / create-drop / update / validate / none. `update` kolon değişikliğini ekleme sanır → eski kolon kalır, veri sessizce ayrışır. Şema Flyway'in; `validate` sadece uyumu kontrol eder, uymazsa uygulama açılmaz.

**41.** hashCode → bucket index; çakışanlar linked list'te. 8 eleman aşılınca red-black tree'ye dönüşür (O(n)→O(log n)). Load factor 0.75 aşılınca kapasite ikiye katlanır (rehash).

**42.** id save'de null→42 değişir; hashCode id'ye dayansaydı nesnenin bucket'ı değişir, `HashSet.contains` içindeki nesneyi bulamazdı. Çözüm: sabit (sınıf bazlı) hashCode + `id != null` şartlı equals.

**43.** UPDATE'e `WHERE id=? AND version=?` eklenir, version +1 yazılır. A okur(v5), B okur(v5); A yazar → v6, 1 satır; B yazar → koşul v5 tutmaz, 0 satır → Hibernate `OptimisticLockException` fırlatır. Kilit yok, çakışma yazma anında yakalanır.

**44.** `customers.stream().map(Customer::getLoans)` → `Stream<List<Loan>>` (iç içe). `customers.stream().flatMap(c -> c.getLoans().stream())` → `Stream<Loan>` (düz) — tüm müşterilerin tüm kredileri tek akışta.

**45.** Merge function — aynı anahtar ikinci kez gelirse ne yapılacağı: `toMap(k, v, (a, b) -> a)`. Yazılmazsa duplicate key'de `IllegalStateException`.

**46.** Producer Extends, Consumer Super: okuyacaksan `? extends`, yazacaksan `? super`. `List<? extends Loan>`'a eklenemez (derleyici gerçek alt tipi bilmez — `List<SpotLoan>` olabilir, içine RevolvingLoan girmemeli); sadece Loan olarak okunur.

**47.** Checked olsaydı her katman imzasına `throws` yazardı; ayrıca `@Transactional` varsayılan olarak SADECE unchecked'te rollback yapar — checked exception'da commit eder. Unchecked seçimi hem temizlik hem doğru rollback davranışıdır.

**48.** Hibernate alanın null mu proxy mi olacağını bilmek için karşı tabloyu sorgulamak zorundadır — sorgu atıldıysa lazy kalmaz. Çözümler: ilişkiyi tek yönlü kurmak, `@MapsId` (paylaşılan PK) veya bytecode enhancement.

**49.** İki eşzamanlı istek aynı count'u okur → aynı numara (yine lost update ailesi). Sequence atomiktir, DB tekil değer verir. Biz `@Query(value="select nextval('customer_number_seq')", nativeQuery=true)` ile çağırdık.

**50.** 15 saf unit test 0.075 sn, 5 Mockito testi 0.58 sn, 6 H2 slice testi 12.65 sn — 168 kat fark; bu yüzden piramidin tabanı unit testtir. H2'nin bedeli: prod dialect'i (PG'ye özgü SQL, sequence) test edilmez; çözüm Testcontainers ile Docker'da gerçek PostgreSQL.
