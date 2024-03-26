import 'package:freezed_annotation/freezed_annotation.dart';

import '../../../core/models/customer.dart';

part 'list_client_state.freezed.dart';

@freezed
class ListClientState with _$ListClientState {
  const ListClientState._();

  const factory ListClientState({
    @Default([]) List<Customer> customers,
    @Default(false) bool isLoading,
    String? currentDistrict,
    String? currentWard,
    String? message,
    Error? error,
  }) = _ListClientState;
}
