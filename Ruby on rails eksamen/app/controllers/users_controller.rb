class UsersController < ApplicationController
  before_action :set_user, only: [:show, :edit, :update, :destroy]
  before_action :require_admin, only: [:index]
  skip_before_action :require_login, :only=>[:new,:create]


  # GET /users
  # GET /users.json
  def index
    @users = User.all.order(:user_profile)
  end

  # GET /users/1
  # GET /users/1.json
  def show
    require_same_user(@user.id)
  end

  # GET /users/new
  def new
    @user = User.new
  end

  # GET /users/1/edit
  def edit
    require_same_user(@user.id)
  end

  # POST /users
  # POST /users.json
  def create
    @user = User.new(user_params)

    respond_to do |format|
      if @user.save
        flash[:success] = "welcome!"
        format.html { redirect_to home_url,success: "User #{@user.name} was successfully created." }
        format.json { render home_url }
      else
        format.html { render :new }
        format.json { render json: @user.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /users/1
  # PATCH/PUT /users/1.json
  def update
    respond_to do |format|
      if @user.update(user_params)
        format.html { redirect_back(fallback_location: home_path,success: "User #{@user.name} was successfully updated.") }
        format.json { render :show, status: :ok, location: @user }
      else
        format.html { render :edit }
        format.json { render json: @user.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /users/1
  # DELETE /users/1.json
  def destroy
    require_same_user(@user.id)
    @user.destroy
    respond_to do |format|
      format.html { redirect_back(fallback_location: home_path,info: "User #{@user.name} was successfully destroyed.") }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_user
      @user = User.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def user_params
      params.require(:user).permit(:name, :email, :password_digest, :user_profile, :password_digest_confirmation, :boo, :image)
    end
end