class SessionsController < ApplicationController
  skip_before_action :require_login, :only=>[:new,:create]

  def new
  end

  # POST /session
  def create
    user = User.authenticate(params[:email], params[:password_digest])
    if user
      reset_session
      session[:user_id] = user.id
      redirect_to home_path, :success => 'Logged in!'
    else
      redirect_to home_path, :warning => 'Invalid email or password'
    end
  end

  def destroy
    session[:user_id] = nil
    redirect_to home_path, :info => 'Logged out!'
  end

end
