package com.example.android4

import kotlin.random.Random

class DataRepository {
    val LIST_SIZE = 20
    companion object {
        private var INSTANCE: DataRepository? = null
        fun getInstance(): DataRepository {
            if (INSTANCE == null) {
                INSTANCE = DataRepository()
            }
            return INSTANCE as DataRepository
        }
    }

    fun getData(): MutableList<Todo> {
        val data: MutableList<Todo> = mutableListOf()
        for (i in 0..LIST_SIZE) {
            val title = getRandomTitle()
            val subtitle = getRandomTaskSubtitle()
            val category: Category = getRandomCategory()
            val description = generateRandomDescription()
            val dueTo = generateRandomDate()
            val importance = Random.nextInt(1, 6)
            val paid = Random.nextBoolean()
            val todo = Todo(title, subtitle, category, description, dueTo, importance, paid)
            data.add(todo)
        }
        return data
    }

    private fun getRandomTitle(): String {
        val taskWords = mutableListOf(
            "Zakupy", "Umyć", "Przygotować", "Zaplanować", "Naprawić", "Przeczytać",
            "Rozpocząć", "Zorganizować", "Napisać", "Podlewać",
            "Odwiedzić", "Zbudować", "Obejrzeć", "Posprzątać", "Odkryć",
            "Posłuchać", "Zrobić", "Odwieźć", "Przerobić", "Wypróbować",
            "Sprawdzić", "Uczyć się", "Zainstalować", "Zjeść", "Zarezerwować",
            "Pomalować", "Poznać", "Przejść", "Zwiedzić", "Poczekać",
            "Odpocząć", "Uczestniczyć", "Poczytać", "Odpocząć", "Odpocząć",
            "Podnosić", "Wyrzucić", "Zmienić", "Otworzyć", "Dowiedzieć się",
            "Zaprosić", "Ograniczyć", "Odkrywać", "Prowadzić", "Podpatrywać",
            "Osiągnąć", "Odnaleźć", "Odwiedzać", "Zorganizować"
        )
        return taskWords.random()
    }

    fun getRandomTaskSubtitle(): String {
        val taskDescriptions = mutableListOf(
            "Realizacja zakupów spożywczych", "Czyszczenie samochodu", "Przygotowanie prezentacji",
            "Planowanie wakacji", "Naprawa zepsutego sprzętu", "Czytanie nowej książki",
            "Rozpoczęcie kursu online", "Organizacja spotkania z przyjaciółmi", "Napisanie listu do rodziny",
            "Podlewanie kwiatów", "Malowanie ścian w domu", "Udekorowanie pokoju",
            "Obejrzenie filmu dokumentalnego", "Wyprowadzenie psa na spacer", "Zdobycie nowej umiejętności",
            "Zakup nowych butów sportowych", "Przygotowanie pysznego obiadu", "Zorganizowanie gry planszowej",
            "Przerobienie starego mebla", "Wypróbowanie nowego przepisu", "Udanie się na koncert",
            "Sprawdzenie nowych wiadomości", "Uczenie się nowego języka", "Zainstalowanie nowego oprogramowania",
            "Zjedzenie zdrowego śniadania", "Zarezerwowanie miejsca w restauracji", "Odwiedzenie muzeum",
            "Obejrzenie nowego serialu", "Odpoczynek na świeżym powietrzu", "Uczestnictwo w wydarzeniu kulturalnym",
            "Poczytanie interesującej książki", "Odwiedzenie nowego miejsca", "Zwiedzenie ciekawego muzeum",
            "Odpoczynek na plaży", "Uczestnictwo w warsztatach", "Poczytanie artykułów naukowych",
            "Podniesienie ciężarów", "Wyrzucenie niepotrzebnych rzeczy", "Zmiana wyglądu pokoju",
            "Otwarcie nowej książki", "Dowiedzenie się nowych faktów", "Odwiedzenie kawiarni",
            "Zaproszenie przyjaciół na kolację", "Ograniczenie czasu na media społecznościowe",
            "Odkrywanie nowych smaków", "Prowadzenie dziennika wydarzeń", "Podpatrywanie ciekawych technik",
            "Osiągnięcie nowego celu", "Odnalezienie ukrytego skarbu", "Odwiedzenie nowej wystawy",
            "Zorganizowanie przyjęcia urodzinowego", "Przygotowanie zdrowego lunchu", "Odkrywanie piękna natury"
        )
        return taskDescriptions.random()
    }

    fun getRandomCategory(): Category {
        val categories = Category.values()
        return categories.random()
    }

    private fun generateRandomDescription(): String {
        val taskDescriptions = mutableListOf(
            "Przygotuj szczegółowy plan działania na nadchodzący tydzień, uwzględniając priorytety i terminy.",
            "Zorganizuj przestrzeń roboczą, usuń niepotrzebne przedmioty i uporządkuj dokumenty.",
            "Napisz listę celów długoterminowych i określ kroki, które pomogą je osiągnąć.",
            "Zaplanuj trening na dziś, uwzględniając różnorodność ćwiczeń i czas trwania.",
            "Przeczytaj co najmniej jedno rozdział z książki z dziedziny, której chcesz się nauczyć.",
            "Zorganizuj spotkanie z przyjaciółmi, ustal datę i miejsce oraz przygotuj listę tematów do rozmowy.",
            "Opracuj menu na obiad, uwzględniając zdrowe składniki i zrównoważoną dietę.",
            "Zidentyfikuj i zrealizuj jeden krok w kierunku osiągnięcia swojego długoterminowego celu.",
            "Przygotuj listę rzeczy, które chciałbyś zrobić podczas nadchodzących wakacji.",
            "Zainstaluj nowe oprogramowanie na komputerze i dostosuj je do swoich preferencji.")
        return taskDescriptions.random()
    }

    private fun generateRandomDate(): String {
        val day = Random.nextInt(8, 30)
        return "$day.12.2023"
    }
}