require 'test_helper'

class CategoriesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @category = categories(:one)
  end

  test "should not_get index_without_permission" do
    get categories_url
    assert_response :found
  end
end
