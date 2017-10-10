class WelcomeController < ApplicationController
  skip_before_action :require_login, :only => [:index]


def index
  @books = Book.search(params[:search]).searchcategories(params[:searchcategories]).paginate(:page => params[:page])
end

  # Sets the number of books to paginate to 3 for testing purposes
  WillPaginate.per_page = 3

end
