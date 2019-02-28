import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICallLogAction } from 'app/shared/model/call-log-action.model';
import { CallLogActionService } from './call-log-action.service';

@Component({
    selector: 'jhi-call-log-action-delete-dialog',
    templateUrl: './call-log-action-delete-dialog.component.html'
})
export class CallLogActionDeleteDialogComponent {
    callLogAction: ICallLogAction;

    constructor(
        protected callLogActionService: CallLogActionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.callLogActionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'callLogActionListModification',
                content: 'Deleted an callLogAction'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-call-log-action-delete-popup',
    template: ''
})
export class CallLogActionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ callLogAction }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CallLogActionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.callLogAction = callLogAction;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/call-log-action', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/call-log-action', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
