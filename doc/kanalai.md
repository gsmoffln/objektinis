# Kanalai
### Duomenų modelis

##### Serveris
Serverį sudaro N kanalų ir M vartotojų.

##### Kanalai
Kanalą sudaro: pavadinimas ir
pranešimų žurnalas.

##### Pranešimas
Pranešimas turi autorių, datą ir tekstą;
taip pat būseną:
  1. Paruoštas siųsti.
  2. Išsiųstas.
    
##### Vartotojas
Prisijungimo vardas, gautų pranešimų sąrašas.

```java
class Server{
    public List<Channel> channels;
    public List<User> users;
}

class Channel{}
class User{}
```