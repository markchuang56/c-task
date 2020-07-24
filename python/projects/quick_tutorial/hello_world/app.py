from waitress import serve
from pyramid.config import Configurator
from pyramid.response import Response

def hello_worldX(request):
    print('Incoming request')
    return Response('<body><h1>How do you \n 中文 turn this on!!</h1></body>')

if __name__=='__main__':
    with Configurator() as config:
        config.add_route('Ahello', '/')
        config.add_view(hello_worldX, route_name='Ahello')
        app = config.make_wsgi_app()
    serve(app, host='0.0.0.0', port=6543)
