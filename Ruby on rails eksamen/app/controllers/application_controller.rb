class ApplicationController < ActionController::Base
  protect_from_forgery with: :exception
  before_action :require_login

  include ActiveModel::AttributeAssignment
  helper_method :current_user
  helper_method :require_login
  helper_method :require_admin
  helper_method :require_moderator
  helper_method :current_admin
  helper_method :get_categories
  helper_method :require_same_user
  helper_method :count_books_for_sale
  helper_method :get_avg_rating
  helper_method :current_moderator

  private

  add_flash_types :success, :warning, :danger, :info

  def count_books_for_sale(book_id)
    @get_count = ForSale.where(:book_id => book_id).count
  end

  def get_avg_rating(num)
    @get_avg_rating = Rating.where(:book_id => num).average(:score)
  end

  def get_categories
    @categories = Category.all
  end

  def current_user
    @current_user ||= User.find(session[:user_id]) if session[:user_id]
  rescue ActiveRecord::RecordNotFound => e
    @current_user = nil
  end

  def current_admin
    @current_admin = current_user && current_user.user_profile == 'admin'
  rescue ActiveRecord::RecordNotFound => e
    @current_admin = nil
  end

  def current_moderator
    @current_moderator = current_user && ((current_user.user_profile == 'moderator') || (current_user.user_profile == 'admin'))
  rescue ActiveRecord::RecordNotFound => e
    @current_moderator = nil
  end

  def require_login
    unless current_user
      redirect_to home_url, warning: 'You must be logged in to access this page'
    end
  end

  def require_admin
    unless current_user && current_user.user_profile.to_s == 'admin'
      redirect_to home_url, danger: 'You are not authorized to access this page'
    end
  end

  def require_moderator
    unless (current_user && current_user.user_profile.to_s == 'moderator') || current_admin
      redirect_to home_url, danger: 'You are not authorized to access this page'
    end
  end

  def require_same_user(user_id)
    unless (current_user && current_user.id == user_id) || current_admin || current_moderator
      redirect_to home_url, danger: 'You are not authorized to access this page'
    end
  end


end
