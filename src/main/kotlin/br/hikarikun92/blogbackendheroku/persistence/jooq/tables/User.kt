/*
 * This file is generated by jOOQ.
 */
package br.hikarikun92.blogbackendheroku.persistence.jooq.tables


import br.hikarikun92.blogbackendheroku.persistence.jooq.DefaultSchema
import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.records.UserRecord

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Identity
import org.jooq.Name
import org.jooq.Record
import org.jooq.Row2
import org.jooq.Schema
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class User(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, UserRecord>?,
    aliased: Table<UserRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<UserRecord>(
    alias,
    DefaultSchema.DEFAULT_SCHEMA,
    child,
    path,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of <code>user</code>
         */
        val USER = User()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<UserRecord> = UserRecord::class.java

    /**
     * The column <code>user.id</code>.
     */
    val ID: TableField<UserRecord, Int?> = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "")

    /**
     * The column <code>user.username</code>.
     */
    val USERNAME: TableField<UserRecord, String?> = createField(DSL.name("username"), SQLDataType.VARCHAR(255).nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<UserRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<UserRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>user</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>user</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>user</code> table reference
     */
    constructor(): this(DSL.name("user"), null)
    override fun getSchema(): Schema = DefaultSchema.DEFAULT_SCHEMA
    override fun getIdentity(): Identity<UserRecord, Int?> = super.getIdentity() as Identity<UserRecord, Int?>
    override fun getPrimaryKey(): UniqueKey<UserRecord> = Internal.createUniqueKey(User.USER, DSL.name("KEY_user_PRIMARY"), arrayOf(User.USER.ID), true)
    override fun getKeys(): List<UniqueKey<UserRecord>> = listOf(
          Internal.createUniqueKey(User.USER, DSL.name("KEY_user_PRIMARY"), arrayOf(User.USER.ID), true)
    )
    override fun `as`(alias: String): User = User(DSL.name(alias), this)
    override fun `as`(alias: Name): User = User(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): User = User(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): User = User(name, null)

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row2<Int?, String?> = super.fieldsRow() as Row2<Int?, String?>
}
