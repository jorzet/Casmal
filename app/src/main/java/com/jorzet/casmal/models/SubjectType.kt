/*
 * Copyright [2020] [Jorge Zepeda Tinoco]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jorzet.casmal.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 08/08/19.
 */

enum class SubjectType(val value: String) {
    @SerializedName("clinic")
    @Expose
    CLINIC("Clínica"),
    @SerializedName("cardiology")
    @Expose
    CARDIOLOGY("Cardiología"),
    @SerializedName("surgery_anesthesiology")
    @Expose
    SURGERY_ANESTHESIOLOGY("Cirugía Anestesiología"),
    @SerializedName("anatomy")
    @Expose
    ANATOMY("Anatomía"),
    @SerializedName("endocrinology")
    @Expose
    ENDOCRINOLOGY("Endocrinología"),
    @SerializedName("pharmacology")
    @Expose
    PHARMACOLOGY("Farmacología"),
    @SerializedName("physiology")
    @Expose
    PHYSIOLOGY("Fisiología"),
    @SerializedName("gastrology")
    @Expose
    GASTROLOGY("Gastrologia"),
    @SerializedName("gynecology")
    @Expose
    GYNECOLOGY("Ginecología"),
    @SerializedName("hematology")
    @Expose
    HEMATOLOGY("Hematología"),
    @SerializedName("immunology")
    @Expose
    IMMUNOLOGY("Inmunología"),
    @SerializedName("pneumology_dermatology")
    @Expose
    PNEUMOLOGY_DERMATOLOGY("Neumología Dermatología"),
    @SerializedName("pneumology")
    @Expose
    PNEUMOLOGY("Neumología"),
    @SerializedName("neurology")
    @Expose
    NEUROLOGY("Neurología"),
    @SerializedName("oncology")
    @Expose
    ONCOLOGY("Oncología"),
    @SerializedName("psychiatry")
    @Expose
    PSYCHIATRY("Psiquiatría"),
    @SerializedName("nephrology_urology")
    @Expose
    UROLOGY("Nefrología Urología"),
    @SerializedName("nephrology")
    @Expose
    NEPHROLOGY("Nefrología"),
    @SerializedName("dermatology")
    @Expose
    DERMATOLOGY("Dermatología"),
    @SerializedName("embryology")
    @Expose
    EMBRYOLOGY("Embriología"),
    @SerializedName("pathology")
    @Expose
    PATHOLOGY("Patología"),
    @SerializedName("traumatology_orthopedics")
    @Expose
    TRAUMATOLOGY_ORTHOPEDICS("Traumatologia Ortopedia"),
    @SerializedName("biochemistry")
    @Expose
    BIOCHEMISTRY("Bioquímica"),
    @SerializedName("epidemiology")
    @Expose
    EPIDEMIOLOGY("Epidemiología"),
    @SerializedName("histology")
    @Expose
    HISTOLOGY("Histología"),
    @SerializedName("microbiology")
    @Expose
    MICROBIOLOGY("Microbiología"),
    @SerializedName("genetics")
    @Expose
    GENETICS("Genética"),
    @SerializedName("otorhinolaryngology")
    @Expose
    OTORHINOLARYNGOLOGY("Otorrinolaringología"),
    @SerializedName("ophthalmology")
    @Expose
    OPHTHALMOLOGY("Oftalmología"),
    @SerializedName("infectology")
    @Expose
    INFECTOLOGY("Infectología"),
    @SerializedName("gastroenterology")
    @Expose
    GASTROENTEROLOGY("Gastroenterología"),
    @SerializedName("pediatrics")
    @Expose
    PEDIATRICS("Pediatría"),
    NONE("none")
}