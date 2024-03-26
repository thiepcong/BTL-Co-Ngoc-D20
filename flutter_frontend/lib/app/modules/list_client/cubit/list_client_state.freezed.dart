// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'list_client_state.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$ListClientState {
  List<Customer> get customers => throw _privateConstructorUsedError;
  bool get isLoading => throw _privateConstructorUsedError;
  String? get currentDistrict => throw _privateConstructorUsedError;
  String? get currentWard => throw _privateConstructorUsedError;
  String? get message => throw _privateConstructorUsedError;
  Error? get error => throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $ListClientStateCopyWith<ListClientState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ListClientStateCopyWith<$Res> {
  factory $ListClientStateCopyWith(
          ListClientState value, $Res Function(ListClientState) then) =
      _$ListClientStateCopyWithImpl<$Res, ListClientState>;
  @useResult
  $Res call(
      {List<Customer> customers,
      bool isLoading,
      String? currentDistrict,
      String? currentWard,
      String? message,
      Error? error});
}

/// @nodoc
class _$ListClientStateCopyWithImpl<$Res, $Val extends ListClientState>
    implements $ListClientStateCopyWith<$Res> {
  _$ListClientStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? customers = null,
    Object? isLoading = null,
    Object? currentDistrict = freezed,
    Object? currentWard = freezed,
    Object? message = freezed,
    Object? error = freezed,
  }) {
    return _then(_value.copyWith(
      customers: null == customers
          ? _value.customers
          : customers // ignore: cast_nullable_to_non_nullable
              as List<Customer>,
      isLoading: null == isLoading
          ? _value.isLoading
          : isLoading // ignore: cast_nullable_to_non_nullable
              as bool,
      currentDistrict: freezed == currentDistrict
          ? _value.currentDistrict
          : currentDistrict // ignore: cast_nullable_to_non_nullable
              as String?,
      currentWard: freezed == currentWard
          ? _value.currentWard
          : currentWard // ignore: cast_nullable_to_non_nullable
              as String?,
      message: freezed == message
          ? _value.message
          : message // ignore: cast_nullable_to_non_nullable
              as String?,
      error: freezed == error
          ? _value.error
          : error // ignore: cast_nullable_to_non_nullable
              as Error?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$ListClientStateImplCopyWith<$Res>
    implements $ListClientStateCopyWith<$Res> {
  factory _$$ListClientStateImplCopyWith(_$ListClientStateImpl value,
          $Res Function(_$ListClientStateImpl) then) =
      __$$ListClientStateImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {List<Customer> customers,
      bool isLoading,
      String? currentDistrict,
      String? currentWard,
      String? message,
      Error? error});
}

/// @nodoc
class __$$ListClientStateImplCopyWithImpl<$Res>
    extends _$ListClientStateCopyWithImpl<$Res, _$ListClientStateImpl>
    implements _$$ListClientStateImplCopyWith<$Res> {
  __$$ListClientStateImplCopyWithImpl(
      _$ListClientStateImpl _value, $Res Function(_$ListClientStateImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? customers = null,
    Object? isLoading = null,
    Object? currentDistrict = freezed,
    Object? currentWard = freezed,
    Object? message = freezed,
    Object? error = freezed,
  }) {
    return _then(_$ListClientStateImpl(
      customers: null == customers
          ? _value._customers
          : customers // ignore: cast_nullable_to_non_nullable
              as List<Customer>,
      isLoading: null == isLoading
          ? _value.isLoading
          : isLoading // ignore: cast_nullable_to_non_nullable
              as bool,
      currentDistrict: freezed == currentDistrict
          ? _value.currentDistrict
          : currentDistrict // ignore: cast_nullable_to_non_nullable
              as String?,
      currentWard: freezed == currentWard
          ? _value.currentWard
          : currentWard // ignore: cast_nullable_to_non_nullable
              as String?,
      message: freezed == message
          ? _value.message
          : message // ignore: cast_nullable_to_non_nullable
              as String?,
      error: freezed == error
          ? _value.error
          : error // ignore: cast_nullable_to_non_nullable
              as Error?,
    ));
  }
}

/// @nodoc

class _$ListClientStateImpl extends _ListClientState {
  const _$ListClientStateImpl(
      {final List<Customer> customers = const [],
      this.isLoading = false,
      this.currentDistrict,
      this.currentWard,
      this.message,
      this.error})
      : _customers = customers,
        super._();

  final List<Customer> _customers;
  @override
  @JsonKey()
  List<Customer> get customers {
    if (_customers is EqualUnmodifiableListView) return _customers;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(_customers);
  }

  @override
  @JsonKey()
  final bool isLoading;
  @override
  final String? currentDistrict;
  @override
  final String? currentWard;
  @override
  final String? message;
  @override
  final Error? error;

  @override
  String toString() {
    return 'ListClientState(customers: $customers, isLoading: $isLoading, currentDistrict: $currentDistrict, currentWard: $currentWard, message: $message, error: $error)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ListClientStateImpl &&
            const DeepCollectionEquality()
                .equals(other._customers, _customers) &&
            (identical(other.isLoading, isLoading) ||
                other.isLoading == isLoading) &&
            (identical(other.currentDistrict, currentDistrict) ||
                other.currentDistrict == currentDistrict) &&
            (identical(other.currentWard, currentWard) ||
                other.currentWard == currentWard) &&
            (identical(other.message, message) || other.message == message) &&
            (identical(other.error, error) || other.error == error));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(_customers),
      isLoading,
      currentDistrict,
      currentWard,
      message,
      error);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$ListClientStateImplCopyWith<_$ListClientStateImpl> get copyWith =>
      __$$ListClientStateImplCopyWithImpl<_$ListClientStateImpl>(
          this, _$identity);
}

abstract class _ListClientState extends ListClientState {
  const factory _ListClientState(
      {final List<Customer> customers,
      final bool isLoading,
      final String? currentDistrict,
      final String? currentWard,
      final String? message,
      final Error? error}) = _$ListClientStateImpl;
  const _ListClientState._() : super._();

  @override
  List<Customer> get customers;
  @override
  bool get isLoading;
  @override
  String? get currentDistrict;
  @override
  String? get currentWard;
  @override
  String? get message;
  @override
  Error? get error;
  @override
  @JsonKey(ignore: true)
  _$$ListClientStateImplCopyWith<_$ListClientStateImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
