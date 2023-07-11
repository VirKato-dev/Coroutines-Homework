package otus.homework.coroutines

import otus.homework.coroutines.model.FactAndPict

interface ICatsView {

    fun populate(fact: FactAndPict)
}