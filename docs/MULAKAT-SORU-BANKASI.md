# Mülakat Soru Bankası

Her sorunun altında model cevap. Cevabı önce kendin ver, sonra karşılaştır.
Gün ilerledikçe bölümler eklenir.

---

## BÖLÜM 1 — Bankacılık Domain (Gün 1)

**1. Müşteri, hesap ve kredi arasındaki ilişkiyi açıklayın.**
Müşteri kök varlıktır; birden fazla hesabı ve kredisi olabilir (1:N). Kullandırımda para bir hesaba geçer. Kredi bankanın aktifinde bir alacaktır; bu yüzden müşteri bazında toplam risk izlenir.

**2. Limit tahsis ile kullandırım farkı nedir?**
Tahsis "şu tutara kadar izin" demektir, para çıkışı yoktur. Kullandırım paranın fiilen hesaba geçmesidir; kullanılan tutar artar, kullanılabilir limit azalır. Kredi kartı limiti ile kartla harcama analojisi.

**3. Nakit ve gayrinakdi kredi farkı?**
Nakitte para çıkar, banka faiz kazanır. Gayrinakdide banka para vermez, garanti verir (teminat mektubu gibi) ve komisyon kazanır; risk şarta bağlıdır — müşteri yükümlülüğünü yerine getirmezse banka öder.

**4. Spot kredi nedir, taksitliden farkı ne?**
Faiz kullandırım anında sabitlenir; anapara+faiz vade sonunda tek seferde ödenir, ara taksit yoktur. Taksitlide geri ödeme vadelere yayılır. Spot tek satırlık, taksitli n satırlık ödeme planı üretir.

**5. Rotatif kredinin çalışma mantığı?**
Limit dahilinde istenildiğinde kullandırım ve geri ödeme yapılır; ödenen tutar limiti yeniden açar. Faiz günlük kullanılan bakiye üzerinden hesaplanır, dönemsel tahsil edilir.

**6. Rotatif kredide iki şube aynı anda kullandırım yaparsa ne olur?**
İkisi de aynı kalan limiti okur, toplam kullandırım limiti aşar — lost update problemi. Çözüm: `@Version` ile optimistic locking (çakışan yazma OptimisticLockException alır) veya pessimistic lock.

**7. Eşit taksitli kredide ilk taksitin faizi neden son taksitten yüksek?**
Faiz her ay kalan borç üzerinden hesaplanır; başta borç yüksek olduğundan taksitin çoğu faizdir, borç azaldıkça anapara payı artar (amortisman).

**8. Krediyi 12. ayda erken kapatan müşteri ne öder?**
Kalan taksitlerin toplamını değil, kalan anaparayı (+ işlemiş faiz + varsa erken kapatma ücreti). Gelecek taksitlerin içindeki faiz henüz doğmamıştır.

**9. Sabit ve değişken faizde riski kim taşır?**
Sabitte banka (fonlama maliyeti yükselirse zarar eder), değişkende müşteri (taksit artabilir).

**10. Taksitleri veritabanında nasıl modellersiniz?**
Kredi 1:N taksit. Her taksitte no, vade, anapara, faiz, vergi, toplam, kalan bakiye, durum. Anapara/faiz ayrı kolonlar (muhasebe + erken kapatma ister). Tutarlar NUMERIC/BigDecimal. Vade+durum kolonlarına composite index.

---

## BÖLÜM 2 — Java Core + Java 8 (40 soru)

### Collections

**11. List, Set, Map farkı?**
List sıralı ve tekrara izin verir; Set tekrarsızdır; Map anahtar-değer tutar, anahtar tekildir. Kredi listesi List, tekil müşteri numaraları Set, müşteriNo→müşteri eşlemesi Map.

**12. ArrayList vs LinkedList — ne zaman hangisi?**
ArrayList dizi tabanlıdır: index erişimi O(1), ortaya ekleme O(n). LinkedList düğüm tabanlıdır: uçlara ekleme O(1), erişim O(n). Pratikte neredeyse her zaman ArrayList; LinkedList yalnızca uçlardan yoğun ekleme/çıkarma (deque) senaryosunda.

**13. HashMap içeride nasıl çalışır?**
Anahtarın hashCode'u bucket index'ine çevrilir. Çakışan anahtarlar aynı bucket'ta linked list olarak tutulur; Java 8'den itibaren bir bucket 8 elemanı aşarsa red-black tree'ye dönüşür (O(n)→O(log n)). Doluluk load factor'ü (0.75) aşınca kapasite iki katına çıkar (rehash).

**14. equals/hashCode sözleşmesi nedir, neden ikisi birlikte override edilir?**
equals eşit diyorsa hashCode aynı olmak zorundadır. Sadece equals override edilirse eşit iki nesne farklı bucket'lara düşer; HashMap/HashSet contains bulamaz.

**15. HashMap vs Hashtable vs ConcurrentHashMap?**
Hashtable her metodu synchronized yapan eski sınıftır, kullanılmaz. HashMap thread-safe değildir. ConcurrentHashMap segment/bucket bazlı kilitleme ile eşzamanlı erişimde hem güvenli hem hızlıdır.

**16. HashSet vs TreeSet vs LinkedHashSet?**
HashSet sırasız O(1); TreeSet sıralı (Comparable/Comparator) O(log n); LinkedHashSet ekleme sırasını korur.

**17. Comparable vs Comparator?**
Comparable sınıfın kendi doğal sırasıdır (`compareTo`, sınıfın içinde, tek). Comparator dışarıdan verilen sıralamadır, istenildiği kadar yazılır: `list.sort(Comparator.comparing(Loan::getAmount).reversed())`.

**18. ConcurrentModificationException ne zaman fırlar?**
For-each ile gezerken koleksiyonu doğrudan değiştirirsen (fail-fast iterator). Çözüm: `iterator.remove()`, `removeIf`, veya stream/filter ile yeni koleksiyon.

**19. Array ile ArrayList farkı?**
Array sabit boyutlu ve kovaryanttır, primitive tutabilir. ArrayList dinamik büyür, sadece nesne tutar (autoboxing), Collections API'siyle çalışır.

**20. İterasyon sırasında eleman silmenin doğru yolları?**
`iterator.remove()`, `collection.removeIf(pred)`, veya filtreleyip yeni liste oluşturmak. For-each içinde `list.remove()` CME fırlatır.

### Generics

**21. Generics nedir, ne kazandırır?**
Tip parametresiyle derleme zamanı tip güvenliği: `List<Loan>` içine Customer koyamazsın, cast gerekmez. Hata çalışma zamanından derleme zamanına çekilir.

**22. Type erasure nedir?**
Generic tip bilgisi derlemeden sonra silinir; çalışma zamanında `List<Loan>` sadece List'tir. Bu yüzden `new T()` yazılamaz, `List<Loan>` ile `List<Customer>` aynı Class nesnesidir, overload edilemezler.

**23. `? extends T` vs `? super T` (PECS)?**
Producer Extends, Consumer Super. Veri OKUYACAKSAN `? extends T` (üretici — `List<? extends Loan>`'dan Loan okunur ama eklenemez); YAZACAKSAN `? super T` (tüketici — `List<? super SpotLoan>`'a SpotLoan eklenir).

**24. `List<Object>` ile `List<?>` aynı mı?**
Hayır. `List<Object>` her nesneyi kabul eden somut tiptir; `List<String>` ona atanamaz. `List<?>` "bilinmeyen tipte bir liste"dir; her List atanabilir ama null dışında eklenemez.

**25. Bounded type parameter örneği?**
`<T extends Comparable<T>> T max(List<T> list)` — T'nin compareTo'su olduğu garanti edilir. Projede: `JpaRepository<Customer, Long>` — repository generic'lerin gerçek kullanımı.

### Lambda & Functional Interface

**26. Lambda nedir?**
Tek soyut metotlu bir arayüzün anonim implementasyonunun kısa yazımıdır: `loan -> loan.getAmount()`. Davranışı parametre olarak taşımayı sağlar.

**27. Functional interface nedir?**
Tek soyut metodu olan arayüz (`@FunctionalInterface`). Lambda ancak böyle bir arayüzün yerine geçebilir. default/static metotlar sayılmaz.

**28. Function, Predicate, Consumer, Supplier farkı?**
`Function<T,R>` dönüştürür (apply), `Predicate<T>` boolean test eder (test), `Consumer<T>` tüketir değer dönmez (accept), `Supplier<T>` parametresiz üretir (get). BiFunction iki girdili Function'dır.

**29. Method reference nedir, kaç türü var?**
Lambda'nın mevcut metoda delegasyonu: static (`Character::getNumericValue`), instance metodu tip üzerinden (`Loan::getAmount`), belirli nesne üzerinden (`validator::isValidTckn`), constructor (`Customer::new`).

**30. Lambda dışarıdaki değişkeni kullanabilir mi?**
Evet ama değişken effectively final olmalı — lambda oluşturulduktan sonra değeri değişmemeli. Değiştirilmesi gerekiyorsa tasarım sorunudur (ör. stream reduce kullanılmalı).

**31. Interface'te default metot ne işe yarar?**
Arayüze implementasyonlu metot eklemeyi sağlar; mevcut implementasyonlar kırılmaz. Java 8'de Collection'a `stream()` böyle eklendi. static metot da arayüzde yardımcı fonksiyon barındırır.

**32. Anonim sınıf ile lambda farkı?**
Lambda yalnızca functional interface için çalışır, `this` dış sınıfı gösterir, daha hafiftir (invokedynamic). Anonim sınıf her arayüz/sınıf için olur ve kendi `this`'i vardır.

### Stream API

**33. Stream nedir, koleksiyondan farkı?**
Veri üzerinde fonksiyonel işlem hattıdır; veri saklamaz, kaynaktan akıtır. Koleksiyon depodur, stream işlemdir. Bir stream bir kez tüketilir.

**34. Intermediate vs terminal operation?**
Intermediate (filter, map, sorted) yeni stream döner ve LAZY'dir — terminal gelmeden hiçbiri çalışmaz. Terminal (collect, sum, forEach, findFirst) akışı tüketir ve sonucu üretir.

**35. map vs flatMap?**
map her elemanı bire bir dönüştürür. flatMap her elemanı bir stream'e dönüştürüp düzleştirir: `customers.stream().flatMap(c -> c.getLoans().stream())` → tüm müşterilerin tüm kredileri tek akışta.

**36. filter + map + collect ile 100.000 TL üstü kredilerin unvanları?**
```java
loans.stream().filter(l -> l.getAmount().compareTo(LIMIT) > 0)
     .map(Loan::getCustomerTitle).collect(Collectors.toList());
```

**37. Collectors.groupingBy ne yapar?**
Akışı anahtara göre Map'e gruplar: `loans.stream().collect(groupingBy(Loan::getType))` → tip→krediler. İkinci parametreyle downstream: `groupingBy(Loan::getType, counting())`.

**38. partitioningBy ile groupingBy farkı?**
partitioningBy predicate ile İKİ gruba böler (`Map<Boolean, List>`); groupingBy n gruba böler.

**39. reduce ne yapar?**
Akışı tek değere indirger: `amounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add)`. İlk parametre identity (boş akışta dönen değer).

**40. Stream neden tekrar kullanılamaz?**
Terminal operation akışı tüketir; ikinci kullanım IllegalStateException fırlatır. Yeniden gerekiyorsa kaynaktan yeni stream açılır.

**41. Lazy evaluation'ı örnekle açıklayın.**
`stream().filter(...).map(...)` hiçbir şey çalıştırmaz; `collect()` gelince elemanlar tek tek hattan geçer. `findFirst()` varsa ilk eşleşmede durur (short-circuit) — milyonluk listede büyük kazanç.

**42. parallelStream ne zaman kullanılmaz?**
Küçük veri (thread maliyeti kazançtan büyük), I/O bağımlı işler, paylaşılan mutable state, sıra önemliyse. Ortak ForkJoinPool'u bloklamak diğer parallel stream'leri de yavaşlatır. Para hesabında sıralı akış tercih ederim.

**43. Collectors.toMap tuzağı nedir?**
Aynı anahtar iki kez gelirse IllegalStateException. Üçüncü parametre merge function şarttır: `toMap(k, v, (first, second) -> first)`. Ayrıca value null olamaz.

**44. Stream ile null güvenliği?**
Kaynak null olabilir alanlarda `filter(Objects::nonNull)`; Optional dönen map'lerde `map(...).filter(Optional::isPresent).map(Optional::get)` veya Java 9+ `Optional::stream`.

### Optional

**45. Optional nedir, neden var?**
"Değer olmayabilir" bilgisini tipe taşır; null dönüşün yerini alır. Çağıran boş durumu ele almaya zorlanır → NPE kaynağında yakalanır.

**46. orElse vs orElseGet (klasik tuzak)?**
`orElse(f())` — f() Optional DOLU olsa bile çalışır (argüman önce hesaplanır). `orElseGet(() -> f())` — sadece boşsa çalışır. Pahalı üretimlerde her zaman orElseGet.

**47. Optional.of vs ofNullable?**
`of(null)` anında NPE fırlatır — "burada null olamaz" iddiasıdır. `ofNullable(x)` null'u boş Optional'a çevirir.

**48. orElseThrow kullanımı?**
`repo.findById(id).orElseThrow(() -> new ResourceNotFoundException(...))` — boşsa anlamlı domain exception. get() kullanılmaz (kontrolsüz NoSuchElementException).

**49. Optional nerelerde KULLANILMAZ?**
Alan (field) tipi, metot parametresi, koleksiyon elemanı olarak; serialize edilmez. Doğru yer: dönüş tipi. `Optional<List>` yerine boş liste dönülür.

**50. Optional.map ve filter ne sağlar?**
Null kontrolü olmadan zincir: `findByNo(no).map(CustomerResponse::from).orElseThrow(...)`. filter koşul tutmazsa boş Optional'a çevirir.

---

## BÖLÜM 3 — Spring Core & Boot (Gün 1)

**51. IoC nedir?**
Nesne oluşturma ve bağlama sorumluluğunun uygulamadan container'a devri. Sınıf bağımlılığını new'lemez, dışarıdan alır.

**52. DI nedir, kaç yolu var, hangisi neden tercih edilir?**
Bağımlılığın dışarıdan verilmesi: constructor, setter, field. Constructor tercih edilir: final alanlar, Spring'siz test, bağımlılık imzada görünür, circular dependency açılışta patlar. Tek constructor'da @Autowired gerekmez.

**53. Bean nedir, scope'ları nelerdir?**
Container'ın yönettiği nesne. singleton (varsayılan — tek nesne), prototype (her istekte yeni), request/session (web). Singleton olduğu için bean'lerde mutable state tutulmaz.

**54. @Component, @Service, @Repository, @Controller farkı?**
Hepsi component-scan ile bean olur; fark semantik + davranış: @Repository persistence exception'larını DataAccessException'a çevirir (exception translation), @Controller MVC'ye endpoint tanıtır. @Service bugün saf işaretleyicidir ama katmanı belgeler.

**55. @SpringBootApplication ne içerir?**
@Configuration + @ComponentScan + @EnableAutoConfiguration. Scan bu sınıfın paketinden AŞAĞI çalışır — ana sınıf kök pakette durmalı, yoksa kardeş paketler taranmaz.

**56. Auto-configuration nasıl çalışır?**
Classpath'e bakar: starter-web varsa Tomcat+MVC, datasource url varsa DataSource kurar. @ConditionalOnClass/@ConditionalOnMissingBean ile koşulludur; kendi bean'ini tanımlarsan varsayılanından vazgeçer.

**57. Spring vs Spring Boot?**
Spring framework'tür (IoC, AOP, MVC); Boot onun üzerinde otomatik yapılandırma + starter bağımlılıklar + gömülü sunucu + Actuator getirir. XML/manuel konfigürasyon derdini kaldırır.

**58. application.yml profilleri nasıl çalışır?**
Ortak dosya + application-{profil}.yml birleşir, profil ezer. Aktif profil SPRING_PROFILES_ACTIVE ile seçilir. Şifreler ${ENV_VAR} ile ortamdan gelir, repoya girmez.

**59. Bean lifecycle'da araya nasıl girilir?**
@PostConstruct (bağımlılıklar verildikten sonra) ve @PreDestroy (kapanışta). Daha derin: InitializingBean/DisposableBean veya @Bean(initMethod/destroyMethod).

**60. @Qualifier / @Primary ne zaman gerekir?**
Aynı tipten birden çok bean varsa: @Primary varsayılanı işaretler, @Qualifier("adı") belirli olanı seçer. Projede Map<String, InterestCalculator> injection alternatifi (Gün 2).

---

## BÖLÜM 4 — JPA / Hibernate (Gün 1)

**61. JPA vs Hibernate vs Spring Data JPA?**
JPA standart (arayüz), Hibernate uygulaması (SQL üreten motor), Spring Data JPA üstünde kolaylık katmanı (repository proxy'si + derived query).

**62. Entity lifecycle durumları?**
Transient (yeni, izlenmiyor) → Persistent (persistence context'te, İZLENİYOR) → Detached (context kapandı) + Removed. Persistent nesnede dirty checking çalışır.

**63. save() çağırmadan güncelleme olur mu?**
Evet — transaction içindeki persistent entity'nin setter'ı çağrılırsa commit'te Hibernate farkı görüp UPDATE atar (dirty checking).

**64. @GeneratedValue stratejileri?**
IDENTITY: DB auto-increment, basit ama batch insert yapamaz (id için her insert anında gider). SEQUENCE: id'ler önceden alınır, batch mümkün. AUTO öngörülemez, TABLE kullanılmaz.

**65. @Enumerated neden STRING olmalı?**
ORDINAL sırayı saklar; enum yeniden sıralanınca DB'deki tüm değerler sessizce başka anlama gelir. STRING okunur ve kırılmaz.

**66. 1:1 ilişkide mappedBy hangi tarafta?**
FK'yı taşıyan taraf owning side'dır, @JoinColumn oradadır. mappedBy FK'sız (inverse) tarafa yazılır. Unutulursa Hibernate ikinci bir ilişki/kolon üretir.

**67. cascade ve orphanRemoval farkı?**
cascade işlemi ilişkiye yayar (REMOVE: sahibi silinince çocuk da silinir). orphanRemoval ilişkiden KOPARILAN çocuğu siler (parent.setChild(null) → DELETE).

**68. LAZY vs EAGER; default'lar?**
LAZY dokununca yükler, EAGER hemen. @ManyToOne/@OneToOne default EAGER (tek satır), @OneToMany/@ManyToMany default LAZY (koleksiyon). Pratikte hepsi LAZY'ye çekilir, ihtiyaç halinde join fetch.

**69. @OneToOne'da LAZY neden çoğu zaman çalışmaz?**
mappedBy (inverse) tarafta Hibernate alanın null mu proxy mi olacağını bilmek için karşı tabloyu sorgulamak zorundadır — sorgu atıldıysa lazy kalmadı. Çözüm: tek yönlü ilişki, @MapsId veya bytecode enhancement.

**70. Optimistic vs pessimistic locking?**
Optimistic: kilit yok; @Version ile UPDATE'e version şartı eklenir, çakışan taraf exception alır — çakışmanın nadir olduğu yerde. Pessimistic: SELECT FOR UPDATE ile satır kilitlenir, diğerleri bekler — çakışmanın sık/kritik olduğu yerde.

**71. Persistence context / 1st level cache nedir?**
Transaction boyunca yönetilen entity'lerin haritası. Aynı id ikinci kez findById → SQL atılmaz, context'ten döner. Dirty checking'in dayanağıdır.

**72. Neden şemayı Hibernate değil Flyway yönetiyor?**
ddl-auto=update sessiz veri kaybı riski taşır (kolon renaming'i ekleme olarak görür). Flyway versiyonlu, gözden geçirilmiş, ortamlar arası tutarlı migration sağlar; validate ile entity-şema uyumu yine kontrol edilir.

---

## BÖLÜM 5 — REST / HTTP (Gün 1)

**73. REST nedir?**
Kaynak tabanlı, stateless mimari stil. Kaynaklar isimlerle (URL), işlemler HTTP metotlarıyla ifade edilir; her istek kendi bağlamını taşır.

**74. HTTP metotları ve idempotency?**
GET/PUT/DELETE idempotent (n kez = 1 kez), POST değil (her çağrı yeni kayıt). PUT tam güncelleme, PATCH kısmi. Bu yüzden ödeme gibi işlemlerde POST + idempotency key kullanılır.

**75. 400 vs 422?**
400: istek biçimsel bozuk ("anlamadım"). 422: biçim doğru ama iş kuralı reddetti ("anladım, kabul etmiyorum" — TCKN checksum, mükerrer kayıt).

**76. 401 vs 403?**
401 Unauthorized: kimliğin doğrulanmadı (aslında unauthenticated). 403 Forbidden: kimsin biliyorum, bu kaynağa yetkin yok.

**77. Neden 201 + Location header?**
REST sözleşmesi: kaynak oluşturduysan yeni kaynağın adresini bildir. İstemci hemen GET atabilir.

**78. DTO neden gerekli (4 sebep)?**
Lazy loading patlaması; hassas alan sızıntısı; API-şema kilitlenmesi; çift yönlü ilişkide sonsuz döngü. Entity içeride, DTO sınırda.

**79. @PathVariable / @RequestParam / @RequestBody?**
Path: URL parçası — kaynağın kimliği. Param: query string — filtre/sayfalama. Body: JSON gövde → DTO; @Valid ile doğrulanır.

**80. Pagination nasıl yapılır, neden şart?**
Pageable (page, size, sort) → LIMIT/OFFSET + COUNT; Page<T> içerik + toplam döner. Milyonluk tabloda List dönmek belleği bitirir.

---

## BÖLÜM 6 — Maven / Git (Gün 1)

**81. mvn clean install ne yapar?**
clean target'ı siler; install lifecycle'ı baştan çalıştırır (compile→test→package) ve jar'ı ~/.m2'ye kurar. Test kırıksa build durur.

**82. Dependency scope'ları?**
compile (her yerde), provided (derlemede var çalışmada yok — lombok), runtime (çalışmada var — JDBC driver), test (sadece test — JUnit, H2).

**83. dependencyManagement vs dependencies?**
dependencies fiilen ekler ve miras kalır; dependencyManagement sadece sürümü merkezî sabitler, modül isterse sürümsüz ekler.

**84. Transitive dependency çakışması nasıl çözülür?**
mvn dependency:tree ile görülür; dependencyManagement'ta sürüm sabitlenir veya exclusion yazılır. Maven "nearest wins" kuralı uygular.

**85. Merge vs rebase?**
Merge geçmişleri birleştirir, merge commit üretir. Rebase commit'leri hedefin ucuna yeniden yazar; temiz geçmiş ama kimlikler değişir — paylaşılan dalda yapılmaz.

**86. revert vs reset?**
revert ters commit üretir, paylaşılan dalda güvenli. reset geçmişi geri sarar (soft/mixed/hard), sadece lokal dallarda.

**87. Şifre yanlışlıkla commit'lendi, sonraki commit'te silindi. Güvende mi?**
Hayır — tarihçede duruyor. Şifre yanmıştır: derhal değiştirilir; gerekirse history rewrite (filter-repo) + force push, ama sır rotasyonu esastır.

**88. PR süreci nasıl işler, neden var?**
feature dalı → push → PR → code review → merge. Bankadaki dual control'ün yazılım karşılığı: hiçbir kod tek kişinin kararıyla canlıya gitmez.

---

## BÖLÜM 7 — Test (Gün 1)

**89. Unit vs integration test?**
Unit tek sınıfı bağımlılıklarından izole test eder (mock, milisaniye). Integration bileşenlerin birlikte çalışmasını test eder (Spring context, DB — saniyeler). Piramit: altta çok unit.

**90. @Mock vs @InjectMocks vs @Spy?**
@Mock dublör üretir; @InjectMocks gerçek test edilen sınıfı kurup mock'ları constructor'ına verir; @Spy gerçek nesneyi sarar, sadece ezilen metotlar sahtedir.

**91. when/thenReturn vs thenAnswer?**
thenReturn sabit değer döner. thenAnswer invocation'a göre dinamik cevap üretir: `inv -> inv.getArgument(0)` (save'in aldığını dönmesi).

**92. verify ne işe yarar? never()?**
Mock'la etkileşimi doğrular: `verify(repo).save(...)` çağrıldı mı, `verify(repo, never()).save(...)` — kural ihlalinde KAYDEDİLMEDİĞİNİ kanıtlar.

**93. ArgumentCaptor ne zaman gerekir?**
Mock'a giden argümanın içeriğini incelemek için: service'in save'e gönderdiği Customer'ın numarası sequence'ten mi geldi, status ACTIVE mi.

**94. Matcher tuzağı nedir?**
Bir argümanda any() kullandıysan hepsinde matcher şart: `method(any(), eq("sabit"))`. Karışık kullanım InvalidUseOfMatchersException fırlatır.

**95. @SpringBootTest vs @DataJpaTest vs @WebMvcTest?**
SpringBootTest tüm context (yavaş, uçtan uca için). DataJpaTest sadece JPA dilimi + H2 + her test rollback. WebMvcTest sadece web katmanı + MockMvc, service mock'lanır.

**96. H2 ile test etmenin bedeli ne, çözümü ne?**
Prod dialect'i (PG'ye özgü SQL, sequence, index davranışı) test edilmez. Kritik sorgular için Testcontainers ile Docker'da gerçek PostgreSQL kullanılır.
