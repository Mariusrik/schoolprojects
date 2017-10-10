class BooksController < ApplicationController
  before_action :set_book, only: [:show, :edit, :update, :destroy]
  before_action :require_moderator, only: [:edit, :update, :index]
  before_action :require_admin, only: [:destroy]
  skip_before_action :require_login, :only => [:show]

  attr_accessor :books

  # GET /books
  # GET /books.json
  def index
    @books = Book.all
    @comment = Comment.new
    @rating = Rating.new
    @forSale = ForSale.new
  end

  # GET /books/1
  # GET /books/1.json
  def show
  end

  # GET /books/new
  def new
    @book = Book.new
  end

  # GET /books/1/edit
  def edit
  end

  # POST /books
  # POST /books.json
  def create
    @book = Book.new(book_params)
    @book.categories.push

    respond_to do |format|
      if @book.save
        format.html { redirect_to home_url, success: 'Book was successfully created.' }
        format.json { render :show, status: :created, location: @book }
      else
        format.html { render :new }
        format.json { render json: @book.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /books/1
  # PATCH/PUT /books/1.json
  def update
    @book.categories.push
    respond_to do |format|
      if @book.update(book_params)
        format.html { redirect_back(fallback_location: home_path,success: 'Book was successfully updated.') }
        format.json { render :show, status: :ok, location: @book }
      else
        format.html { render :edit }
        format.json { render json: @book.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /books/1
  # DELETE /books/1.json
  def destroy
    @book.destroy
    respond_to do |format|
      format.html { redirect_back(fallback_location: home_path,info: 'Book was successfully destroyed.') }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_book
      @book = Book.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def book_params
      params.require(:book).permit(:bookId, :title, :description, :image, category_ids: [])
    end
end
