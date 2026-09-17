package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("AMS", appName)
  }

  @Test
  fun `curriculum repository contains class 10 and 12 subjects`() {
    val class10Subjects = com.example.data.CurriculumRepository.getSubjectsForGrade(com.example.model.GradeClass.CLASS_10)
    val class12Subjects = com.example.data.CurriculumRepository.getSubjectsForGrade(com.example.model.GradeClass.CLASS_12)
    org.junit.Assert.assertTrue("Class 10 should have subjects", class10Subjects.isNotEmpty())
    org.junit.Assert.assertTrue("Class 12 should have subjects", class12Subjects.isNotEmpty())
  }
}
