#from pyramid.response import Response
from pyramid.view import view_config


# First view. available at http://localhost:6543
@view_config(route_name='home', renderer='home.pt')
def home(request):
    return {'name': 'Home View'}
    #return Response('<body>Visit <a href="/howdy">hello</a></body>')

# /howdy
@view_config(route_name='hello', renderer='home.pt')
def hello(request):
    return {'name': 'Hello View'}
    #return Response('<body>Go back <a href="/">home</a></body>')
