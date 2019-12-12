/*
 * Copyright [2019] [Jorge Zepeda Tinoco]
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

enum class SubjectType(name: String) {
    @SerializedName("clinic")
    @Expose
    CLINIC("clinic"),
    @SerializedName("cardiology")
    @Expose
    CARDIOLOGY("cardiology"),
    @SerializedName("surgery_anesthesiology")
    @Expose
    SURGERY_ANESTHESIOLOGY("surgery_anesthesiology"),
    @SerializedName("anatomy")
    @Expose
    ANATOMY("anatomy"),
    @SerializedName("endocrinologia")
    @Expose
    ENDOCRINOLOGY("endocrinologia"),
    @SerializedName("pharmacology")
    @Expose
    PHARMACOLOGY("pharmacology"),
    @SerializedName("physiology")
    @Expose
    PHYSIOLOGY("physiology"),
    @SerializedName("gastrology")
    @Expose
    GASTROLOGY("gastrology"),
    @SerializedName("gynecology")
    @Expose
    GYNECOLOGY("gynecology"),
    @SerializedName("hematology")
    @Expose
    HEMATOLOGY("hematology"),
    @SerializedName("immunology")
    @Expose
    IMMUNOLOGY("immunology"),
    @SerializedName("pneumology_dermatology")
    @Expose
    PNEUMOLOGY("pneumology_dermatology"),
    @SerializedName("neurology")
    @Expose
    NEUROLOGY("neurology"),
    @SerializedName("oncology")
    @Expose
    ONCOLOGY("oncology"),
    @SerializedName("psychiatry")
    @Expose
    PSYCHIATRY("psychiatry"),
    @SerializedName("nephrology_urology")
    @Expose
    UROLOGY("nephrology_urology"),
    @SerializedName("nephrology")
    @Expose
    NEPHROLOGY("nephrology"),
    @SerializedName("dermatology")
    @Expose
    DERMATOLOGY("dermatology"),
    @SerializedName("embryology")
    @Expose
    EMBRYOLOGY("embryology"),
    @SerializedName("pathology")
    @Expose
    PATHOLOGY("pathology"),
    @SerializedName("traumatology_orthopedics")
    @Expose
    TRAUMATOLOGY_ORTHOPEDICS("traumatology_orthopedics"),
    @SerializedName("biochemistry")
    @Expose
    BIOCHEMISTRY("biochemistry"),
    @SerializedName("epidemiology")
    @Expose
    EPIDEMIOLOGY("epidemiology"),
    @SerializedName("histology")
    @Expose
    HISTOLOGY("histology"),
    @SerializedName("microbiology")
    @Expose
    MICROBIOLOGY("microbiology"),
    @SerializedName("genetics")
    @Expose
    GENETICS("genetics"),
    @SerializedName("otorhinolaryngology")
    @Expose
    OTORHINOLARYNGOLOGY("otorhinolaryngology"),
    @SerializedName("ophthalmology")
    @Expose
    OPHTHALMOLOGY("ophthalmology"),
    @SerializedName("infectology")
    @Expose
    INFECTOLOGY("infectology"),
    @SerializedName("gastroenterology")
    @Expose
    GASTROENTEROLOGY("gastroenterology"),
    @SerializedName("pediatrics")
    @Expose
    PEDIATRICS("pediatrics"),
    NONE("none")
}