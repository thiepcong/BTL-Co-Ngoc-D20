// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'tranfer_mail_state.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$TranferMailState {
  String? get message => throw _privateConstructorUsedError;
  Error? get error => throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $TranferMailStateCopyWith<TranferMailState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $TranferMailStateCopyWith<$Res> {
  factory $TranferMailStateCopyWith(
          TranferMailState value, $Res Function(TranferMailState) then) =
      _$TranferMailStateCopyWithImpl<$Res, TranferMailState>;
  @useResult
  $Res call({String? message, Error? error});
}

/// @nodoc
class _$TranferMailStateCopyWithImpl<$Res, $Val extends TranferMailState>
    implements $TranferMailStateCopyWith<$Res> {
  _$TranferMailStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? message = freezed,
    Object? error = freezed,
  }) {
    return _then(_value.copyWith(
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
abstract class _$$TranferMailStateImplCopyWith<$Res>
    implements $TranferMailStateCopyWith<$Res> {
  factory _$$TranferMailStateImplCopyWith(_$TranferMailStateImpl value,
          $Res Function(_$TranferMailStateImpl) then) =
      __$$TranferMailStateImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String? message, Error? error});
}

/// @nodoc
class __$$TranferMailStateImplCopyWithImpl<$Res>
    extends _$TranferMailStateCopyWithImpl<$Res, _$TranferMailStateImpl>
    implements _$$TranferMailStateImplCopyWith<$Res> {
  __$$TranferMailStateImplCopyWithImpl(_$TranferMailStateImpl _value,
      $Res Function(_$TranferMailStateImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? message = freezed,
    Object? error = freezed,
  }) {
    return _then(_$TranferMailStateImpl(
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

class _$TranferMailStateImpl extends _TranferMailState {
  const _$TranferMailStateImpl({this.message, this.error}) : super._();

  @override
  final String? message;
  @override
  final Error? error;

  @override
  String toString() {
    return 'TranferMailState(message: $message, error: $error)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$TranferMailStateImpl &&
            (identical(other.message, message) || other.message == message) &&
            (identical(other.error, error) || other.error == error));
  }

  @override
  int get hashCode => Object.hash(runtimeType, message, error);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$TranferMailStateImplCopyWith<_$TranferMailStateImpl> get copyWith =>
      __$$TranferMailStateImplCopyWithImpl<_$TranferMailStateImpl>(
          this, _$identity);
}

abstract class _TranferMailState extends TranferMailState {
  const factory _TranferMailState({final String? message, final Error? error}) =
      _$TranferMailStateImpl;
  const _TranferMailState._() : super._();

  @override
  String? get message;
  @override
  Error? get error;
  @override
  @JsonKey(ignore: true)
  _$$TranferMailStateImplCopyWith<_$TranferMailStateImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
