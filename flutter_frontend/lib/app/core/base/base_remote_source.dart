import 'dart:io';

import 'package:dio/dio.dart';

import '../../network/exceptions/api_exception.dart';
import '/app/network/dio_provider.dart';
import '/app/network/error_handlers.dart';
import '/app/network/exceptions/base_exception.dart';

abstract class BaseRemoteSource {
  Dio get dioClient => DioProvider.dioWithHeaderToken;

  Future<Response<T>> callApiWithErrorParser<T>(Future<Response<T>> api) async {
    try {
      Response<T> response = await api;

      if (response.statusCode != HttpStatus.ok ||
          (response.data as Map<String, dynamic>)['statusCode'] !=
              HttpStatus.ok) {}

      return response;
    } on DioException catch (dioError) {
      Exception exception = handleDioError(dioError);
      if (exception is ApiException) {}
      throw exception;
    } catch (error) {
      if (error is BaseException) {
        rethrow;
      }

      throw handleError("$error");
    }
  }
}
