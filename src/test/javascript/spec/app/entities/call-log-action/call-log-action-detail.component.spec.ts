/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ErmesTestModule } from '../../../test.module';
import { CallLogActionDetailComponent } from 'app/entities/call-log-action/call-log-action-detail.component';
import { CallLogAction } from 'app/shared/model/call-log-action.model';

describe('Component Tests', () => {
    describe('CallLogAction Management Detail Component', () => {
        let comp: CallLogActionDetailComponent;
        let fixture: ComponentFixture<CallLogActionDetailComponent>;
        const route = ({ data: of({ callLogAction: new CallLogAction(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CallLogActionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CallLogActionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CallLogActionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.callLogAction).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
