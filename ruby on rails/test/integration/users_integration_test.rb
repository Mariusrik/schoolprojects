require 'test_helper'

class UsersFlowTest < ActionDispatch::IntegrationTest

  def setup
    @category_one = categories(:one)
    @category_two = categories(:two)
    @user = users(:user)
    @admin_one = users(:admin)

    @user_new = User.create(name: "validName", password_digest: "secret123", email: "thisIs@valid.mail")

  end

  test "can see the welcome page" do
    get "/"
    assert_select "h1", "Syllabus
        Share your book tips!"
  end

  test "can create new user" do
    get "/sign_up"
    assert_equal 200, status


    post "/users", params: { user: { name: "bob", email: "mail@mail.mail", password_digest: "secret123" }}
    assert_equal 200, status
    assert_equal "/users", path
  end

  test "user can login" do
    get "/log_in"
    assert_equal 200, status

    post "/sessions", params: { email: @user_new.email, password_digest: "secret123" }
    follow_redirect!
    assert_equal 200, status
    assert_equal "/", path
    assert_select "a", :href => 'MyFirstName'
  end

  test "user cannot login with wrong logininformation" do
    get "/log_in"
    assert_equal 200, status

    post "/sessions", params: { email: @user_new.email, password_digest: "ThisIsAWrongPassword" }
    follow_redirect!
    assert_equal 200, status
    assert_equal "/", path
    assert_select "a", :href => 'Log in'
  end

  #params.require(:user).permit(:name, :email, :password_digest, :user_profile, :password_digest_confirmation, :boo, :image)
  test "can edit user" do
    post "/sessions", params: { email: @user_new.email, password_digest: "secret123" }

    name = "newName"
    patch user_path(@user_new), params: { user: {
            name: "newName",
            email: @user_new.email,
            password_digest: "secret123",
            user_profile: @user_new.user_profile,
            password_digest_confirmation: "secret123",
            image: nil
    } }

    follow_redirect!
    assert_equal 200, status
    assert_equal "/", path
    assert_select "a", :href => 'newName'

  end

  test "can log out" do

  end

  test "cannot destroy user unless logged in" do
    assert_no_difference 'User.count' do
      delete user_path(@user_new)
    end
  end

  test "cannot destroy yourself" do
=begin
    post "/sessions", params: { email: @user_new.email, password_digest: "secret123" }
    follow_redirect!
    assert_equal 200, status
    assert_equal "/", path
    assert_select "p", "Logged in!"

    assert_difference 'User.count' do
      delete user_path(@user_new)
    end

    follow_redirect!
    assert_equal 200, status
    assert_equal "/", path
    assert_select "p", "User newName was successfully deleted."
=end
  end

end
