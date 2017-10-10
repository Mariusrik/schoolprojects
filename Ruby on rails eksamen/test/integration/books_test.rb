require 'test_helper'

class BlogFlowTest < ActionDispatch::IntegrationTest

  def setup
    @category_one = categories(:one)
    @category_two = categories(:two)
    @user = users(:user)
    @admin_one = users(:admin)

    @user_new = User.create(name: "validName", password_digest: "secret123", email: "thisIs@valid.mail")

  end

  test "can create a new book" do
    # Login
    post "/sessions", params: { email: @user_new.email, password_digest: "secret123" }

    get "/books/new"
    assert_response :success

    assert_no_difference 'Book.count' do
    post "/books", params: {
        book: {
            title: "myTitle",
            description: "myDesc",
            image: "image.png",
            category_id: @category_one.id
        }}
    end

    assert_response :success
  end

end
