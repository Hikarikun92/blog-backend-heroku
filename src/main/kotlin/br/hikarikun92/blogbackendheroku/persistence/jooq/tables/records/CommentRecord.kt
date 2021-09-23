/*
 * This file is generated by jOOQ.
 */
package br.hikarikun92.blogbackendheroku.persistence.jooq.tables.records


import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.Comment

import org.jooq.Field
import org.jooq.Record1
import org.jooq.Record5
import org.jooq.Row5
import org.jooq.impl.UpdatableRecordImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class CommentRecord() : UpdatableRecordImpl<CommentRecord>(Comment.COMMENT), Record5<Int?, String?, String?, Int?, Int?> {

    var id: Int?
        set(value) = set(0, value)
        get() = get(0) as Int?

    var title: String?
        set(value) = set(1, value)
        get() = get(1) as String?

    var body: String?
        set(value) = set(2, value)
        get() = get(2) as String?

    var userId: Int?
        set(value) = set(3, value)
        get() = get(3) as Int?

    var postId: Int?
        set(value) = set(4, value)
        get() = get(4) as Int?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<Int?> = super.key() as Record1<Int?>

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow(): Row5<Int?, String?, String?, Int?, Int?> = super.fieldsRow() as Row5<Int?, String?, String?, Int?, Int?>
    override fun valuesRow(): Row5<Int?, String?, String?, Int?, Int?> = super.valuesRow() as Row5<Int?, String?, String?, Int?, Int?>
    override fun field1(): Field<Int?> = Comment.COMMENT.ID
    override fun field2(): Field<String?> = Comment.COMMENT.TITLE
    override fun field3(): Field<String?> = Comment.COMMENT.BODY
    override fun field4(): Field<Int?> = Comment.COMMENT.USER_ID
    override fun field5(): Field<Int?> = Comment.COMMENT.POST_ID
    override fun component1(): Int? = id
    override fun component2(): String? = title
    override fun component3(): String? = body
    override fun component4(): Int? = userId
    override fun component5(): Int? = postId
    override fun value1(): Int? = id
    override fun value2(): String? = title
    override fun value3(): String? = body
    override fun value4(): Int? = userId
    override fun value5(): Int? = postId

    override fun value1(value: Int?): CommentRecord {
        this.id = value
        return this
    }

    override fun value2(value: String?): CommentRecord {
        this.title = value
        return this
    }

    override fun value3(value: String?): CommentRecord {
        this.body = value
        return this
    }

    override fun value4(value: Int?): CommentRecord {
        this.userId = value
        return this
    }

    override fun value5(value: Int?): CommentRecord {
        this.postId = value
        return this
    }

    override fun values(value1: Int?, value2: String?, value3: String?, value4: Int?, value5: Int?): CommentRecord {
        this.value1(value1)
        this.value2(value2)
        this.value3(value3)
        this.value4(value4)
        this.value5(value5)
        return this
    }

    /**
     * Create a detached, initialised CommentRecord
     */
    constructor(id: Int? = null, title: String? = null, body: String? = null, userId: Int? = null, postId: Int? = null): this() {
        this.id = id
        this.title = title
        this.body = body
        this.userId = userId
        this.postId = postId
    }
}
