package ru.avem.posshop.utils

import ru.avem.posshop.database.entities.Protocol
import ru.avem.posshop.database.entities.ProtocolInsulation
import ru.avem.posshop.database.entities.TestObjectsType

object Singleton {
    lateinit var currentProtocol: Protocol
    lateinit var currentProtocolInsulation: ProtocolInsulation
    lateinit var currentTestItem: TestObjectsType
}
