import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/app/network/exceptions/bad_request_exception.dart';
import '../repository/list_client_repository.dart';
import 'list_client_state.dart';

class ListClientCubit extends Cubit<ListClientState> {
  ListClientCubit(this._repo) : super(const ListClientState());

  final ListClientRepository _repo;

  void setCurrentDistrict(String value) {
    emit(state.copyWith(currentDistrict: value, currentWard: null));
  }

  void setCurrentWard(String value) {
    emit(state.copyWith(currentWard: value));
  }

  void getListClient() async {
    if (state.currentDistrict == null || state.currentWard == null) {
      emit(state.copyWith(message: "Vui lòng chọn quận/huyện và xã/phường"));
      return;
    }
    emit(state.copyWith(isLoading: true, message: null));
    try {
      final res = await _repo.getListClient(
        district: state.currentDistrict!,
        ward: state.currentWard!,
        page: 1,
        size: 10,
      );
      emit(state.copyWith(customers: res, isLoading: false));
    } catch (e) {
      if (e is BadRequestException) {
        emit(state.copyWith(isLoading: false, message: e.message));
        return;
      }
      rethrow;
    }
  }
}
