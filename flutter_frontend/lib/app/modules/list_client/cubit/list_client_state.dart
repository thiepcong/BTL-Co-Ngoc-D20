import 'package:freezed_annotation/freezed_annotation.dart';

import '../../../core/models/customer.dart';
import '../../../core/models/stat_model.dart';

part 'list_client_state.freezed.dart';

@freezed
class ListClientState with _$ListClientState {
  const ListClientState._();

  const factory ListClientState({
    StatModel? currentItem,
    @Default([]) List<Customer> customerMails,
    @Default(false) bool isLoading,
    String? currentFilter,
    String? currentDistrict,
    String? currentWard,
    DateTime? currentSelectDate,
    String? message,
    Error? error,
  }) = _ListClientState;
}
