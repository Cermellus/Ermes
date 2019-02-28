/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ErmesTestModule } from '../../../test.module';
import { CreditRequestDetailComponent } from 'app/entities/credit-request/credit-request-detail.component';
import { CreditRequest } from 'app/shared/model/credit-request.model';

describe('Component Tests', () => {
    describe('CreditRequest Management Detail Component', () => {
        let comp: CreditRequestDetailComponent;
        let fixture: ComponentFixture<CreditRequestDetailComponent>;
        const route = ({ data: of({ creditRequest: new CreditRequest(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditRequestDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CreditRequestDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CreditRequestDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.creditRequest).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
