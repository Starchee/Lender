package com.starchee.lender.domain.entities

import java.lang.RuntimeException


open class NetworkException: RuntimeException()

class NoNetWorkException(): NetworkException()

class BadRequestException(): NetworkException()

class NotFoundException(): NetworkException()