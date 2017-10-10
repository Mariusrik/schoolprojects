Rails.application.routes.draw do

  get 'log_in' => 'sessions#new', :as => 'log_in'
  get 'log_out' => 'sessions#destroy', :as => 'log_out'
  get 'sign_up' => 'users#new', :as => 'sign_up'
  get 'sessions/new'

  root :to => 'welcome#index', :as => 'home'
  resources :comments
  resources :ratings
  resources :users
  resources :books do
    resources :comments
    resources :ratings
  end
  resources :categories
  resources :sessions
  resources :welcome
  resources :for_sales

  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
end
