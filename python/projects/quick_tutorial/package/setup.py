from setuptools import setup

# List of dependencies installed via `pip install -e . `
# by virtue of the Setuptools `instll_requires` value below.
requires = [
    'pyramid',
    'waitress',
]

setup(
    name='tutorial',
    intstall_requires=requires,
)