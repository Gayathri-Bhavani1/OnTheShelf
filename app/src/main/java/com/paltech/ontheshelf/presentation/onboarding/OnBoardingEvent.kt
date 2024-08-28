package com.paltech.ontheshelf.presentation.onboarding

sealed class OnBoardingEvent {

     data object SaveAppEntry: OnBoardingEvent()

}