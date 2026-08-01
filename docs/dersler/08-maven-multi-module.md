# Ders 8 — Maven ve Multi-Module Yapı

## pom.xml'in 5 parçası
1. **GAV**: groupId + artifactId + version = kütüphanenin adresi. `-SNAPSHOT` = geliştirme sürümü.
2. **parent**: `spring-boot-starter-parent` → uyumlu sürüm seti miras alınır, sürüm yazmayız.
3. **dependencies + scope**:
   | Scope | Nerede var | Örnek |
   |---|---|---|
   | compile | her yerde | spring-boot-starter-web |
   | provided | derlemede var, çalışmada yok | lombok |
   | runtime | derlemede yok, çalışmada var | postgresql driver |
   | test | sadece test | junit, h2 |
4. **Transitive dependency**: 1 starter → ~40 kütüphane. `mvn dependency:tree` ile görülür.
5. **Lifecycle**: `validate → compile → test → package → verify → install → deploy`
   `mvn install` öncesindeki TÜM fazları çalıştırır (testler dahil).

## dependencyManagement vs dependencies (klasik soru)
- `dependencies`: bağımlılığı **fiilen ekler**, alt modüllere miras kalır.
- `dependencyManagement`: sadece **sürümü sabitler**; modül isterse ekler, sürüm yazmaz.

## Multi-module
```
kredilendirme (packaging: pom — aggregator, kod yok)
├── kredilendirme-common   (ortak event/DTO — kopya kod önler)
├── kredi-service          (spring-boot-maven-plugin → çalıştırılabilir jar)
└── disbursement-service   (Gün 3)
```

## Güvenlik notu
Kurumlar Maven Central'a doğrudan çıkmaz; Nexus/Artifactory proxy'sinden onaylı sürüm çeker
(Log4Shell dersi). Mülakatta söylemesi artı puan.

## 🎯 Mülakat
**S: `mvn clean install` ne yapar?**
C: `clean` target'ı siler; `install` yaşam döngüsünü baştan çalıştırır — derleme, testler,
paketleme — ve jar'ı lokal repoya (~/.m2) kurar. Testler geçmezse build kırılır.
