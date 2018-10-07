package io.rybalkinsd.kotlinbootcamp.geometry

/**
 * Entity that can physically intersect, like flame and player
 */
interface Collider {
    fun isColliding(other: Collider): Boolean
}
/**
 * 2D point with integer coordinates
 */
class Point(val x: Int, val y: Int) : Collider {

    override fun equals(other: Any?): Boolean {
         return (other is Point) && (this.x == other.x) && (this.y == other.y)
    }

    override fun isColliding(other: Collider): Boolean {
        return when (other) {
            is Point -> this.equals(other)
            is Bar -> other.isColliding(this)
            else -> false
        }
    }
}

/**
 * Bar is a rectangle, which borders are parallel to coordinate axis
 * Like selection bar in desktop, this bar is defined by two opposite corners
 * Bar is not oriented
 * (It does not matter, which opposite corners you choose to define bar)
 */
class Bar(val firstCornerX: Int, val firstCornerY: Int, val secondCornerX: Int, val secondCornerY: Int) : Collider {

    override fun isColliding(other: Collider): Boolean {
        return when (other) {
            is Point -> PointCollidingBar(other)
            is Bar -> BarCollidingBar(other)
            else -> false
        }
    }

    fun PointCollidingBar(other: Point): Boolean {
        return if ((other.x <= maxOf(this.firstCornerX, this.secondCornerX))&&(other.x >= minOf(this.firstCornerX, this.secondCornerX))&&(other.y <= maxOf(this.firstCornerY, this.secondCornerY))&&(other.y >= minOf(this.firstCornerY, this.secondCornerY))) true else false
    }

    fun BarCollidingBar(other: Bar): Boolean {
        var ur = false
        val p1 = Point(other.firstCornerX, other.firstCornerY)
        val p2 = Point(other.firstCornerX, other.secondCornerY)
        val p3 = Point(other.secondCornerX, other.firstCornerY)
        val p4 = Point(other.secondCornerX, other.secondCornerY)
        if ((PointCollidingBar(p1))||(PointCollidingBar(p2))||(PointCollidingBar(p3))||(PointCollidingBar(p4))) ur = true else ur = false
        return ur
    }
}