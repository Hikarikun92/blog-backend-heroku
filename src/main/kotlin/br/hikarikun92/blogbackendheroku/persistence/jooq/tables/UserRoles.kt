/*
 * This file is generated by jOOQ.
 */
package br.hikarikun92.blogbackendheroku.persistence.jooq.tables


import br.hikarikun92.blogbackendheroku.persistence.jooq.DefaultSchema
import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.records.UserRolesRecord

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Name
import org.jooq.Record
import org.jooq.Row2
import org.jooq.Schema
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.impl.DSL
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class UserRoles(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, UserRolesRecord>?,
    aliased: Table<UserRolesRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<UserRolesRecord>(
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
         * The reference instance of <code>user_roles</code>
         */
        val USER_ROLES = UserRoles()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<UserRolesRecord> = UserRolesRecord::class.java

    /**
     * The column <code>user_roles.user_id</code>.
     */
    val USER_ID: TableField<UserRolesRecord, Int?> = createField(DSL.name("user_id"), SQLDataType.INTEGER.nullable(false), this, "")

    /**
     * The column <code>user_roles.roles</code>.
     */
    val ROLES: TableField<UserRolesRecord, String?> = createField(DSL.name("roles"), SQLDataType.VARCHAR(255).nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<UserRolesRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<UserRolesRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>user_roles</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>user_roles</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>user_roles</code> table reference
     */
    constructor(): this(DSL.name("user_roles"), null)
    override fun getSchema(): Schema = DefaultSchema.DEFAULT_SCHEMA
    override fun `as`(alias: String): UserRoles = UserRoles(DSL.name(alias), this)
    override fun `as`(alias: Name): UserRoles = UserRoles(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): UserRoles = UserRoles(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): UserRoles = UserRoles(name, null)

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row2<Int?, String?> = super.fieldsRow() as Row2<Int?, String?>
}