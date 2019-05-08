package bc.com.flops

interface StateChanged <E> {

    fun onChange(list: List<E>)

}