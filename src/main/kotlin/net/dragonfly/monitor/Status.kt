package net.dragonfly.monitor

enum class Status {
    AVAILABLE,
    OFFLINE;

    override fun toString(): String = name.toLowerCase()
}