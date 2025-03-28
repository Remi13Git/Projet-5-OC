describe('Register spec', () => {
    it('should register an user', () => {

        // 1. Test d'inscription réussie
      cy.visit('/register')
      cy.intercept('POST', '/api/auth/register', {
        statusCode: 200,
        body: {},
      }).as('registerRequest')
      cy.get('input[formControlName=email]').type('newuser@studio.com')
      cy.get('input[formControlName=firstName]').type('New')
      cy.get('input[formControlName=lastName]').type('User')
      cy.get('input[formControlName=password]').type('test!1234')
      cy.get('button[type="submit"]').should('not.be.disabled').click()
      cy.wait('@registerRequest').its('response.statusCode').should('eq', 200)
      cy.url().should('include', '/login')
    });
});