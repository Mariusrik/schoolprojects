require 'test_helper'

class UserTest < ActiveSupport::TestCase

  test "user name cannot be empty" do
    user = User.new(password_digest: "validPassword123", email: "thisIs@valid.mail")
    assert user.invalid?
  end

  test "user mail cannot be empty" do
    user = User.new(name: "validName", password_digest: "validPassword123")
    assert user.invalid?
  end

  test "user password cannot be empty" do
    user = User.new(name: "validName", email: "thisIs@valid.mail")
    assert user.invalid?
  end

  test "user should be created when name password mail entered" do
    user = User.new(name: "validName", password_digest: "validPassword123", email: "thisIs@valid.mail")
    assert user.valid?
  end

  test "email must be uniqeue" do
    user = User.create(name: "validName", password_digest: "validPassword123", email: "thisIs@valid.mail")
    assert user.valid?

    user2 = User.create(name: "validName", password_digest: "validPassword123", email: "thisIs@valid.mail")
    assert user2.invalid?
  end

  test "format on email must be followed" do
    user = User.new(name: "validName", password_digest: "validPassword123", email: "invalidMail")
    assert user.invalid?

    user2 = User.new(name: "validName", password_digest: "validPassword123", email: "invalid@Mail")
    assert user2.invalid?

    user3 = User.new(name: "validName", password_digest: "validPassword123", email: "valid@Mail.no")
    assert user3.valid?
  end

  test "password must have atleast one number" do
    user = User.new(name: "validName", password_digest: "invalidPassword", email: "valid@Mail.no")
    assert user.invalid?
  end

  test "password must be 8 long" do
    user = User.new(name: "validName", password_digest: "toShort", email: "valid@Mail.no")
    assert user.invalid?
  end

  test "password is valid when 8 chars long and 1 number" do
    user = User.new(name: "validName", password_digest: "enogh123", email: "valid@Mail.no")
    assert user.valid?
  end

  test "created and created at is automaticly added" do
    user = User.create(name: "validName", password_digest: "enogh123", email: "valid@Mail.no")
    assert user.valid?

    assert_not_nil user.created_at
    assert_not_nil user.updated_at
  end

  test "user profile should be set to user by default" do
    user = User.new(name: "validName", password_digest: "enogh123", email: "valid@Mail.no")
    assert user.valid?

    assert_equal user.user_profile, "user"
  end

  test "salt is automaticly added" do
    user = User.create(name: "validName", password_digest: "enogh123", email: "valid@Mail.no")
    assert user.valid?

    assert_not_nil user.password_salt
  end
end
