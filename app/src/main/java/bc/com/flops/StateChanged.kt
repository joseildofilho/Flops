package bc.com.flops

/**
 * Padrão observable
 * Escrevendo a roda novamento ...
 * */
interface StateChanged <E> {
    fun onChange(value: E)
}