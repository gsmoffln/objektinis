# Kolekcijos

#### Terminai
 1. **`Kolekcija`, `Rinkiniu`** (angl. _„Collection“_) vadinsime objektą, kuris savyje turi
    **0, 1, …, ∞** kitų klasės **T** objektų – **`elementų`** arba **`narių`**.
 
 2. Kolekcija šiame kurse vadinsime daugiau **matematinį**, abstraktesnį apibrėžimą nei
    `Java Collection Framework` – bandydami apimti taip pat ir kolekcijų realizacijas Scala, Kotlin, XText,
    C#, Scheme, EcmaScript, Python, PHP kalbose.
    
    Skirtumai bus pažymėti. Jeigu toks skirtumas kode ar dokumentacijoje nebus aprašytas, tokį
    atvejį laikysime taisytina klaida.
 
 3. Apibrėždami konkretų kolekcijos tipą (rūšį, matematinį modelį), _a-priori_ jam suteiksime tik minimalius apribojimus.
    Pavyzdžiui: mes nesakome, kad kolekcija turi būtinai baigtinį elementų skaičių, nors standartinėje Java realizacijoje
    visos kolekcijos yra baigtinis.
    
    Konkretus pavyzdys: sveikųjų skaičių aibė apima visus skaičius 0, 0+1=1, 1+1=2, 2+1=3 ir atitinkamai
    0-1=-1, -1-1=-2,-2-1=-3, ir taip toliau. Tokia aibė neturi pradžios ir pabaigos, konkretaus elementų skaičiaus.
    
    Kita vertus, mes galime apibrėžti ir tokios kolekcijos `didumą` kitos kolekcijos atžvilgiu: pvz., lyginių skaičių aibė
    taip pat yra begalinė, tačiau turi tik dalį sveikųjų skaičių.
    
    Antras pvz.:, realiųjų skaičių nėra diskreti: tarp bet kurių dviejų jos elementų _a_ ir _b_ taip pat egzistuoja
    be galo daug tarpinių: (_a+b_)/2, (_a_+((_a+b_)/2))/2 ir t.t..
    
    Kalbėdami apie sveikuosius (ir bet kokius kitus) skaičius matematine prasme, _jei nenurodyta kitaip_, laikysime,
    kad jie neturi tokių apribojimų kaip apatinė/viršutinė riba (`Integer.MIN_VALUE`/`MAX_VALUE`) ar ribinis tikslumas (`DOUBLE.ulp()`)
    
 4. Kolekcijos gali turėti savybes, be kitų:
 
    a. **Skait(l)umas**. Rinkinys yra _skait(l)usis_ arba _diskretusis_ (angl. _countable, discrete_), Jei kiekvieną  jo elementą
        galima sutapatint su nesikartojančiu sveikuoju skaičiumi.
        Pavyzdžiui, liet. k. žodžių aibė yra skaitlioji, nes kiekvienas žodis yra sudarytas iš liet. raidžių, kurioms
        galima suteikti kodus A→00, Ą→01, B→02, ..., T→23, S→24, ... Ž→32 ir visą žodį užrašyti natūraliuoju skaičiumi:
        „ŽĄSTAS"=320124230024.
        
    b. **Baigtinumas**. Rinkinys yra _baigtinis_, _išvardijamas_ arba _**ne**begalinis_ (angl. _enumerable, finite_),
       jei jis nėra _begalinis_ – t.y., jei jis yra
       skaitlusis, bet kiekvieną elementą galima sutapatinti su _baigtinio ilgio_ natūraliuoju skaičiumi.
       Pavyzdys: dešimtainių skaitmenų aibė yra _baigtinė_, nes ją sudaro lygiai 10 elementų 0, 1, …, 9.
       Natūraliųjų skaičių aibė savo ruožtų _nėra_ baigtinė, nes už kiekvieną skaičių N egzistuoja didesnis N+1.
       
    c. **Eiliškumas**, **rikiuojamumas** (_order_). Tokio rinkinio elementai turi griežtai apibrėžtą santykį
       _daugiau/mažiau/vienoda_. Šis santykis turi pats tokias savybes:
       
       0. A=A, (identitetas)
       1. jei A=B, tai B=A ir A≠B → B≠A,
       2. A>B → B<A, (simetrija)
       3. A≠B → !(A=B) → (A<B) | (B<A),
       4. A=B & B=C → A=C, (tranzityvumas),
       5. jei A=B, tai netiesa, kad A<B, A>B ar A≠B – ir visi kiti panašūs atvejai,
       6. A>B & B>C = !A>C,
       10000. (gal kažką pamiršau). **Kodėl tai svarbu**: objektai rinkinyje turės realizuoti
       _equals_, _compareTo(other)_ ir _hashCode_ pagal šį apibrėžimą. Kaip pamatysime vėliau,
       praktikoje neįmanoma vienu metu realizuoti ir šio santykio, ir _Liskov pakaitumo principo_
       teigiančio, kad _poklasis_ (_subclass_, i.e. A extends B) privalo turėti identiškas
       savybes _viršklasiui_ (_superclass_).
       
    d. **Eiliškumo** arba **rikiuotės** išlaikymas **pildant** (_sort_). Toks rinkinys visuomet išlaiko nurodytą leksikografinę
       tvarką. Pvz., į sąrašą sudėjus elementus [0, 1, 2, 3, 2, 3, 4, 2, 3], jų peržiūra grąžins [0, 1, 2, 2, 2, 3, 3, 4].
        
    e. Čia galima skirti atvejį, kuomet eiliškumas išlaikomas **modifikuojant** pačius elementus. Standartinės Java
       (ir daugumos kitų universalių programavimo kalbų) kolekcijų bibliotekos (Sorted*, Hash*, *Tree ir pan. klasės)
       neseka savo elementų turinio keitimo.
       
       Tačiau galime nesunkiai įsivaizduoti situaciją: duomenų bazėje saugomį įrašai apie miesto autobusus ir jų nuvažiuotą ridą.
       Keliamas tikslas autobusų parką apkrauti kuo tolygiau, tad įrašai rikiuojami ridos didėjimo tvarka.
       
       Reliacinėje duomenų bazėje būtų sekamas kiekvienas sąrašo elemento lauko _AUTOMOBILIS.RIDA_ pakeitimas.
       Ir pagal tai kaskart perskaičiuojamas **indeksas**. Tokias struktūras vadinsime **indeksuojamomis**. Iš esmės,
       tuomet keliamas reikalavimas, kad modifikuojamas objektas praneštų **visoms** jį turinčioms kolekcijoms apie
       pakeitimo faktą. 
       
       Duomenų struktūrą, realizuojančią **indeksą**, taip ir vadinsime. Objektus, kurie
       gali būti **indeksuojami**, pažymėsime atitinkamai ir suteiksime jiems priemones,
       kad jie praneštų rinkiniams apie pasikeitimus.
       
    f. Vienas duomenų rinkinys gali turėti **keletą indeksų**. Tuomet, jei vienas iš indeksų
       parenkamas pagrindiniu, jis vadinamas **pirminiu raktu** (_primary key_).
       
    g. Indeksai gali būti paremti (apskaičiuojami iš) keleto laukų. Tokius indeksus 
       vadinsime **jungtiniu raktu** (_composite key/index_).
              
    h. Struktūros, turinčios **tik nekintamo** turinio elementus, automatiškai realizuoja
       (e), realizuodamos (d).
       
    Visoms šioms savybėms apibrėžti naudosime Java **interface** raktažodį.
    Konkreti struktūros realizacija įgyvendins (**implements**) atitinkamą interfeisą _tuomet_,
    ir _tik tuomet_, kai užtikrins tam tikrą savybę, nepriklausomai nuo praktinio taikymo.
    
    Čia mes darome skirtumą tarp _praktinio_ taikymo ir _teorinių_ ypatybių.
    `Java Collections Framework` akcentuoja _praktinį_ taikymą, tuo tarpu mes akcentuosime
    teorinį.
    
 5. Kalbėdami apie modifikuojamus duomenų rinkinius, turime nepamiršti, kad juos gali keisti
    daug procesų vienu metu. Pavyzdžiui, gali būti duomenų bazė paskirstyta per keletą
    serverių skirtinguose žemynuose. Todėl įvardijame _sinchronizacijos_ aspektus,
    kuriuos taip pat žymėsime Java `interface` priemonėmis (kas nėra labai praktiška, bet šio
    modulio turinyje svarbu).
    
