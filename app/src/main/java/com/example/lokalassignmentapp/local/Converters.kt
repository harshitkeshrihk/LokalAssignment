package com.example.lokalassignmentapp.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.lokalassignmentapp.Utils.GsonParser
import com.example.lokalassignmentapp.model.ContactPreference
import com.example.lokalassignmentapp.model.ContentV3
import com.example.lokalassignmentapp.model.Creative
import com.example.lokalassignmentapp.model.FeeDetails
import com.example.lokalassignmentapp.model.JobTag
import com.example.lokalassignmentapp.model.Location
import com.example.lokalassignmentapp.model.PrimaryDetails
import com.example.lokalassignmentapp.model.V3
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Convertors(private val jsonParser: GsonParser) {

    @TypeConverter
    fun fromContactPreference(json: String): ContactPreference? {
        return jsonParser.fromJson(json, object : TypeToken<ContactPreference>() {}.type)
    }

    @TypeConverter
    fun toJsonContactPreference(value: ContactPreference?): String {
        return jsonParser.toJson(value, object : TypeToken<ContactPreference>() {}.type) ?: "null"
    }

    @TypeConverter
    fun fromContentV3(json: String): ContentV3? {
        return jsonParser.fromJson(json, object : TypeToken<ContentV3>() {}.type)
    }

    @TypeConverter
    fun toJsonContentV3(value: ContentV3?): String {
        return jsonParser.toJson(value, object : TypeToken<ContentV3>() {}.type) ?: "null"
    }

    @TypeConverter
    fun fromCreativeList(json: String): List<Creative>? {
        return jsonParser.fromJson(json, object : TypeToken<List<Creative>>() {}.type)
    }

    @TypeConverter
    fun toJsonCreativeList(value: List<Creative>?): String {
        return jsonParser.toJson(value, object : TypeToken<List<Creative>>() {}.type) ?: "[]"
    }

    @TypeConverter
    fun fromJobTagList(json: String): List<JobTag>? {
        return jsonParser.fromJson(json, object : TypeToken<List<JobTag>>() {}.type)
    }

    @TypeConverter
    fun toJsonJobTagList(value: List<JobTag>?): String {
        return jsonParser.toJson(value, object : TypeToken<List<JobTag>>() {}.type) ?: "[]"
    }

    @TypeConverter
    fun fromLocationList(json: String): List<Location>? {
        return jsonParser.fromJson(json, object : TypeToken<List<Location>>() {}.type)
    }

    @TypeConverter
    fun toJsonLocationList(value: List<Location>?): String {
        return jsonParser.toJson(value, object : TypeToken<List<Location>>() {}.type) ?: "[]"
    }

    @TypeConverter
    fun fromListV3(value: List<V3>?): String {
        return jsonParser.toJson(value, object : TypeToken<List<V3>>() {}.type) ?: "[]"
    }

    @TypeConverter
    fun toListV3(value: String): List<V3>? {
        val type = object : TypeToken<List<V3>>() {}.type
        return jsonParser.fromJson(value, type)
    }

    @TypeConverter
    fun fromFeeDetails(value: FeeDetails?): String {
        return jsonParser.toJson(value,object : TypeToken<FeeDetails>(){}.type)?:"[]"
    }

    @TypeConverter
    fun toFeeDetails(value: String): FeeDetails? {
        val type = object : TypeToken<FeeDetails>() {}.type
        return jsonParser.fromJson(value, type)
    }
    @TypeConverter
    fun fromPrimaryDetails(value: PrimaryDetails?): String {
        return jsonParser.toJson(value, object : TypeToken<PrimaryDetails>() {}.type) ?: "[]"
    }

    @TypeConverter
    fun toPrimaryDetails(value: String): PrimaryDetails? {
        val type = object : TypeToken<PrimaryDetails>() {}.type
        return jsonParser.fromJson(value, type)
    }
}
