# Ders 6 — Taksitli Ticari Kredi (Projenin ana ürünü)

## Annüite (eşit taksit) formülü
```
T = A·i·(1+i)ⁿ / ((1+i)ⁿ − 1)
A=500.000  i=0,45/12=0,0375  n=24  →  T ≈ 31.959,43 TL
Toplam ödeme 767.026 → 267.026'sı faiz
```

## Taksitin içi sürekli değişir (amortisman)
```
 1. taksit: faiz 18.750 + anapara 13.209   ← %59'u faiz!
24. taksit: faiz  1.157 + anapara 30.802   ← neredeyse hepsi anapara
```
Faiz her ay **kalan borç** üzerinden hesaplanır → borç azaldıkça faiz payı düşer.

**Tuzak soru:** "12. ayda erken kapatırsam ne öderim?" → Kalan 12 taksitin toplamını DEĞİL,
kalan **anaparayı** (+ o güne dek işlemiş faiz + varsa erken kapatma ücreti).

## Sabit vs Değişken faiz
| | Sabit | Değişken |
|---|---|---|
| Oran | kullandırımda çakılır | fonlama maliyetiyle değişir |
| Taksit | hep aynı | değişir |
| Riski taşıyan | BANKA | MÜŞTERİ |

## Esnek ödeme
Turizm/tarım/inşaat gibi mevsimsel nakit akışında taksitler sezona göre dağıtılır
(kışın 10K, yazın 60K). Toplam aynı, dağılım farklı.

## Veri modeli (1:N)
```
RepaymentSchedule 1 ── N Installment
Installment: no, dueDate, principalAmount, interestAmount, taxAmount,
             totalAmount, remainingPrincipal, status(PAID/UNPAID/OVERDUE)
```
Kuruş farkı **son taksite** yazılır (banka pratiği). Tutarlar `NUMERIC(19,4)` / `BigDecimal`.

## 🎯 Mülakat
**S: Taksitleri veritabanında nasıl modellersiniz?**
C: Kredi ile taksit arasında 1:N; her taksitte no, vade, anapara, faiz, vergi, toplam, kalan
bakiye, durum. Anapara ve faizi ayrı kolonlarda tutarım — muhasebe ve erken kapatma ayrı ister.
Vade+durum kolonlarına birlikte index koyarım; gecikmiş taksit sorgusu ikisiyle çalışır.
