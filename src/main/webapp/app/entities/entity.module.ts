import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'call-log-action',
                loadChildren: './call-log-action/call-log-action.module#ErmesCallLogActionModule'
            },
            {
                path: 'call-log-line',
                loadChildren: './call-log-line/call-log-line.module#ErmesCallLogLineModule'
            },
            {
                path: 'call-log',
                loadChildren: './call-log/call-log.module#ErmesCallLogModule'
            },
            {
                path: 'company',
                loadChildren: './company/company.module#ErmesCompanyModule'
            },
            {
                path: 'contact',
                loadChildren: './contact/contact.module#ErmesContactModule'
            },
            {
                path: 'credit-reason',
                loadChildren: './credit-reason/credit-reason.module#ErmesCreditReasonModule'
            },
            {
                path: 'credit-reference-type',
                loadChildren: './credit-reference-type/credit-reference-type.module#ErmesCreditReferenceTypeModule'
            },
            {
                path: 'credit-request-line',
                loadChildren: './credit-request-line/credit-request-line.module#ErmesCreditRequestLineModule'
            },
            {
                path: 'credit-request',
                loadChildren: './credit-request/credit-request.module#ErmesCreditRequestModule'
            },
            {
                path: 'credit-request-status',
                loadChildren: './credit-request-status/credit-request-status.module#ErmesCreditRequestStatusModule'
            },
            {
                path: 'credit-return-type',
                loadChildren: './credit-return-type/credit-return-type.module#ErmesCreditReturnTypeModule'
            },
            {
                path: 'customer',
                loadChildren: './customer/customer.module#ErmesCustomerModule'
            },
            {
                path: 'prospect',
                loadChildren: './prospect/prospect.module#ErmesProspectModule'
            },
            {
                path: 'product',
                loadChildren: './product/product.module#ErmesProductModule'
            },
            {
                path: 'role',
                loadChildren: './role/role.module#ErmesRoleModule'
            },
            {
                path: 'sales-person',
                loadChildren: './sales-person/sales-person.module#ErmesSalesPersonModule'
            },
            {
                path: 'call-log-line',
                loadChildren: './call-log-line/call-log-line.module#ErmesCallLogLineModule'
            },
            {
                path: 'call-log',
                loadChildren: './call-log/call-log.module#ErmesCallLogModule'
            },
            {
                path: 'credit-request',
                loadChildren: './credit-request/credit-request.module#ErmesCreditRequestModule'
            },
            {
                path: 'ermes-user',
                loadChildren: './ermes-user/ermes-user.module#ErmesErmesUserModule'
            },
            {
                path: 'call-log-line',
                loadChildren: './call-log-line/call-log-line.module#ErmesCallLogLineModule'
            },
            {
                path: 'credit-request',
                loadChildren: './credit-request/credit-request.module#ErmesCreditRequestModule'
            },
            {
                path: 'call-log',
                loadChildren: './call-log/call-log.module#ErmesCallLogModule'
            },
            {
                path: 'credit-request',
                loadChildren: './credit-request/credit-request.module#ErmesCreditRequestModule'
            },
            {
                path: 'app-user',
                loadChildren: './app-user/app-user.module#ErmesAppUserModule'
            },
            {
                path: 'call-log-action',
                loadChildren: './call-log-action/call-log-action.module#ErmesCallLogActionModule'
            },
            {
                path: 'call-log-line',
                loadChildren: './call-log-line/call-log-line.module#ErmesCallLogLineModule'
            },
            {
                path: 'call-log',
                loadChildren: './call-log/call-log.module#ErmesCallLogModule'
            },
            {
                path: 'company',
                loadChildren: './company/company.module#ErmesCompanyModule'
            },
            {
                path: 'contact',
                loadChildren: './contact/contact.module#ErmesContactModule'
            },
            {
                path: 'credit-reason',
                loadChildren: './credit-reason/credit-reason.module#ErmesCreditReasonModule'
            },
            {
                path: 'credit-reference-type',
                loadChildren: './credit-reference-type/credit-reference-type.module#ErmesCreditReferenceTypeModule'
            },
            {
                path: 'credit-request-line',
                loadChildren: './credit-request-line/credit-request-line.module#ErmesCreditRequestLineModule'
            },
            {
                path: 'credit-request',
                loadChildren: './credit-request/credit-request.module#ErmesCreditRequestModule'
            },
            {
                path: 'credit-request-status',
                loadChildren: './credit-request-status/credit-request-status.module#ErmesCreditRequestStatusModule'
            },
            {
                path: 'credit-return-type',
                loadChildren: './credit-return-type/credit-return-type.module#ErmesCreditReturnTypeModule'
            },
            {
                path: 'customer',
                loadChildren: './customer/customer.module#ErmesCustomerModule'
            },
            {
                path: 'prospect',
                loadChildren: './prospect/prospect.module#ErmesProspectModule'
            },
            {
                path: 'product',
                loadChildren: './product/product.module#ErmesProductModule'
            },
            {
                path: 'role',
                loadChildren: './role/role.module#ErmesRoleModule'
            },
            {
                path: 'sales-person',
                loadChildren: './sales-person/sales-person.module#ErmesSalesPersonModule'
            },
            {
                path: 'app-user',
                loadChildren: './app-user/app-user.module#ErmesAppUserModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ErmesEntityModule {}
