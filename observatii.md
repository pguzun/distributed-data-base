Parcurgand materialul, mi-au venit in minte cateva observatii legate de abordarea aceasta de replicare fara lider:

- adauga complexitate pe parte de client. Acesta este responsabil de scriere/citire de pe mai multe noduri, in cazul strategiei "Read-Repair" tot clientul este responsabil pentru corectarea datelor pe nodurile desincronizate, calcul qvorum, etc.
- clientul trebuie sa obtina in prealabil adresele IP ale replicilor. In cloud, containerele sau masinile virtuale pe care ruleaza replicile pot fi repornite sau chiar mutate (cu posibila schimbare de IP). In acest caz clientul trebuie sa actualizeze lista de IP-uri ale replicilor.
- in cazul unui volum mare de scrieri/citiri, se gereneraza trafic aproape inutil pe retea, deoarece clientul aduce acceasi informatie de pe mai multe replici. In cazul in care clientul are o latime mica de banda aceasta poate duce la congestionare.
- pentru a evita cazurile de timeout in care clientul incearca sa scrie/citeasca de pe un nod cazut, ar trebui implementat pe parte de client si un mecanism gen circuit-breaker (care adauga la complexitate). Astfel ar scadea latenta operatiilor in cazul in care unul sau mai multe noduri sunt cazute.
