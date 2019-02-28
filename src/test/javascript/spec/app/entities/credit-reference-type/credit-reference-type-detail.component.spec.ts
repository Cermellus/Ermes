/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ErmesTestModule } from '../../../test.module';
import { CreditReferenceTypeDetailComponent } from 'app/entities/credit-reference-type/credit-reference-type-detail.component';
import { CreditReferenceType } from 'app/shared/model/credit-reference-type.model';

describe('Component Tests', () => {
    describe('CreditReferenceType Management Detail Component', () => {
        let comp: CreditReferenceTypeDetailComponent;
        let fixture: ComponentFixture<CreditReferenceTypeDetailComponent>;
        const route = ({ data: of({ creditReferenceType: new CreditReferenceType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditReferenceTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CreditReferenceTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CreditReferenceTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.creditReferenceType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
