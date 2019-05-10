package bc.com.flops

import java.util.*

data class TarefaTemporal(
    val dataInicio: Date,
    var tempoEsperado: Long,
    var tempoDecorrido:Long
) {
    constructor(): this(Date(), 0, 0)
}