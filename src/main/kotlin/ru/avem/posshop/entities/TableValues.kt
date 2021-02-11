package ru.avem.posshop.entities

import javafx.beans.property.StringProperty

data class TableValuesTest21(
        var voltage: StringProperty,
        var ampere: StringProperty
)

data class TableValuesTest22(
        var voltage: StringProperty,
        var ampere: StringProperty
)

data class TableValuesTest23(
        var voltage: StringProperty,
        var ampere: StringProperty
)

data class TableValuesTest1(
        var place1temp: StringProperty,
        var place1time: StringProperty,
        var place1voltage: StringProperty
)

data class TableValuesTest2(
        var place1amperage: StringProperty,
        var place1time: StringProperty,
        var place1voltage: StringProperty
)

data class TableValuesPlace1Test1(
        var descriptor: StringProperty,
        var place1temp: StringProperty,
        var place1amperage: StringProperty,
        var place1voltage: StringProperty
)

data class TableValuesPlace2Test1(
        var descriptor: StringProperty,
        var place2temp: StringProperty,
        var place2amperage: StringProperty,
        var place2voltage: StringProperty
)

data class TableValuesPlace3Test1(
        var descriptor: StringProperty,
        var place3temp: StringProperty,
        var place3amperage: StringProperty,
        var place3voltage: StringProperty
)

data class TableValuesPlace1Test2(
        var place1amperage: StringProperty,
        var place1voltage: StringProperty
)

data class TableValuesPlace2Test2(
        var place2amperage: StringProperty,
        var place2voltage: StringProperty
)

data class TableValuesPlace3Test2(
        var place3amperage: StringProperty,
        var place3voltage: StringProperty
)

data class TableValuesTestHV(
        var descriptor: StringProperty,
        var voltageSpec: StringProperty,
        var voltage: StringProperty,
        var amperageSpec: StringProperty,
        var amperage: StringProperty,
        var timeSpec: StringProperty,
        var time: StringProperty
)
