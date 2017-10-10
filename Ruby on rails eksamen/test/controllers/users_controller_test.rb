require 'test_helper'

class UsersControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:one)
  end

  test "should_not_get index_without_permission" do
    get users_url
    assert_response :found
  end

  test "should get new" do
    get new_user_url
    assert_response :success
  end
end
