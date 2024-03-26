class LoginResponse {
  final String token;
  final User userInfo;

  LoginResponse({required this.token, required this.userInfo});

  factory LoginResponse.fromJson(Map<String, dynamic> map) {
    return LoginResponse(
      token: map['token'] as String,
      userInfo: User.fromJson(map['userInfo'] as Map<String, dynamic>),
    );
  }
}

class User {
  final int id;
  final String username;
  final String name;
  final String? dateOfBirth;
  final String? address;
  final String email;
  final String phoneNumber;

  User({
    required this.id,
    required this.username,
    required this.name,
    required this.dateOfBirth,
    required this.address,
    required this.email,
    required this.phoneNumber,
  });

  factory User.fromJson(Map<String, dynamic> map) {
    return User(
      id: map['id'] as int,
      username: map['username'] as String,
      name: map['name'] as String,
      dateOfBirth:
          map['dateOfBirth'] != null ? map['dateOfBirth'] as String : null,
      address: map['address'] != null ? map['address'] as String : null,
      email: map['email'] as String,
      phoneNumber: map['phoneNumber'] as String,
    );
  }
}
